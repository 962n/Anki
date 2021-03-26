package com.example.a962n.anki.domain.failure

sealed class Failure {
    object HogeFailure : Failure()
    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
