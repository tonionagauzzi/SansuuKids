package com.vitantonio.nagauzzi.sansuukids.data

import com.russhwolf.settings.Settings
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.Mode

/**
 * メダル情報を永続化するリポジトリ。
 * multiplatform-settingsを使用してKey-Value形式で保存する。
 */
internal class MedalRepository(
    private val settings: Settings = Settings()
) {
    /**
     * メダルを保存する。
     * 既存のメダルより良いメダルの場合のみ更新する。
     */
    fun saveMedal(mode: Mode, level: Level, medal: Medal) {
        val key = createKey(mode, level)
        val currentMedal = getMedal(mode, level)
        if (medal < currentMedal) {
            val value = medal.name
            settings.putString(key, value)
        }
    }

    /**
     * 指定されたモードとレベルのメダルを取得する。
     * @return 保存されているメダル、または null（未獲得の場合）
     */
    fun getMedal(mode: Mode, level: Level): Medal {
        val key = createKey(mode, level)
        val value = settings.getStringOrNull(key)
        return value?.toMedal() ?: Medal.Nothing
    }
}

private fun createKey(mode: Mode, level: Level): String {
    return "medal_${mode.name}_${level.name}"
}

private fun String.toMedal(): Medal = Medal.entries.firstOrNull {
    it.name == this
} ?: Medal.Nothing
