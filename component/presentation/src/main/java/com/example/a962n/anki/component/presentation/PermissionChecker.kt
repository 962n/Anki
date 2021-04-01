package com.example.a962n.anki.component.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * パーミッションをチェックするクラス。
 * 許諾が取れていない場合は許諾ダイアログを表示します。
 *
 * 使い方は以下です。
 * ①Activity or Fragment生成時に本クラスを生成し、メンバ変数に保持してください。
 * ②パーミッションチェック時に[PermissionChecker.check]をコール
 * ③Activity or FragmentのonRequestPermissionsResultにて[PermissionChecker.handlePermissionResult]をコール
 */
class PermissionChecker(private val where: Where) {
    private val list: MutableList<CheckingInfo> = mutableListOf()

    /** どこからパーミッションチェックを行うか */
    sealed class Where {
        /** Activity */
        data class Act(val activity: Activity) : Where()
        /** Fragment */
        data class Fra(val fragment: Fragment) : Where()
    }

    /**
     * 許諾結果
     */
    enum class Result {
        /** ユーザーに拒否された*/
        DENIED,
        /** 許可された(されている) */
        GRANTED
    }

    /**
     * パーミッション種別
     * HACK チェックしたパーミッションを増やしたい場合は本enumに定義を追加してください。
     */
    enum class Permission(val rawValue: String) {
        READ_EXTERNAL(Manifest.permission.READ_EXTERNAL_STORAGE),
        WRITE_EXTERNAL(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private data class CheckingInfo(
        val requestCode: Int,
        val permission: Permission,
        val callback: (result: Result) -> Unit
    )

    /**
     * パーミッションをチェックし、許諾が必要な場合はダイアログを表示する
     * @param requestCode 許諾を取るためのリクエストコード
     * @param permission 対象のパーミッション
     * @param callback 結果通知コールバック
     */
    fun check(requestCode: Int, permission: Permission, callback: (result: Result) -> Unit) {
        val context = when (where) {
            is Where.Act -> {
                where.activity
            }
            is Where.Fra -> {
                where.fragment.context
            }
        } ?: return

        if (ContextCompat.checkSelfPermission(context, permission.rawValue) == PackageManager.PERMISSION_GRANTED) {
            callback(Result.GRANTED)
            return
        }
        when (where) {
            is Where.Act -> {
                ActivityCompat.requestPermissions(
                    where.activity,
                    arrayOf(permission.rawValue),
                    requestCode
                )

            }
            is Where.Fra -> {
                where.fragment.requestPermissions(arrayOf(permission.rawValue), requestCode)
            }
        }
        list.add(
            CheckingInfo(
                requestCode,
                permission,
                callback
            )
        )


    }

    /**
     * 許諾結果の処理を行う
     * Activity or FragmentのonRequestPermissionsResultにて本メソッドをコールしてください。
     * @param requestCode リクエストコード
     * @param permissions パーミッション
     * @param grantResults 許諾結果
     */
    fun handlePermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val targets = list.filter { predicate -> predicate.requestCode == requestCode }
        if (targets.isEmpty()) {
            return
        }
        when (grantResults[0]) {
            PackageManager.PERMISSION_GRANTED -> {
                for (info in targets) {
                    info.callback(Result.GRANTED)
                    list.remove(info)
                }

            }
            else -> {
                for (info in targets) {
                    info.callback(Result.DENIED)
                    list.remove(info)
                }
            }
        }
    }


}