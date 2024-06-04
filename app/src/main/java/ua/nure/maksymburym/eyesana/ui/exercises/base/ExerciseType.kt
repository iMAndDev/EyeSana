package ua.nure.maksymburym.eyesana.ui.exercises.base

import ua.nure.maksymburym.eyesana.R

enum class ExerciseType(val titleRes: Int) {
    LEFT_RIGHT(titleRes = R.string.left_and_right),
    CLOSER_FURTHER(titleRes = R.string.closer_further),
    CURTAINS(titleRes = R.string.curtains),
    EIGHT(titleRes = R.string.eight),
    NIGHT(titleRes = R.string.night)
}