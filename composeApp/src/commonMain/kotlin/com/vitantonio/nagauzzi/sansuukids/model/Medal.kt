package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.result_medal_bronze
import sansuukids.composeapp.generated.resources.result_medal_emoji_bronze
import sansuukids.composeapp.generated.resources.result_medal_emoji_gold
import sansuukids.composeapp.generated.resources.result_medal_emoji_nothing
import sansuukids.composeapp.generated.resources.result_medal_emoji_silver
import sansuukids.composeapp.generated.resources.result_medal_emoji_star
import sansuukids.composeapp.generated.resources.result_medal_gold
import sansuukids.composeapp.generated.resources.result_medal_nothing
import sansuukids.composeapp.generated.resources.result_medal_silver
import sansuukids.composeapp.generated.resources.result_medal_star

/**
 * クイズ結果に応じて獲得できるメダルの種類。
 */
@Serializable
internal enum class Medal {
    Nothing,
    Gold,
    Silver,
    Bronze,
    Star
}

internal val Medal.emojiRes: StringResource
    get() = when (this) {
        Medal.Nothing -> Res.string.result_medal_emoji_nothing
        Medal.Gold -> Res.string.result_medal_emoji_gold
        Medal.Silver -> Res.string.result_medal_emoji_silver
        Medal.Bronze -> Res.string.result_medal_emoji_bronze
        Medal.Star -> Res.string.result_medal_emoji_star
    }

internal val Medal.descriptionRes: StringResource
    get() = when (this) {
        Medal.Nothing -> Res.string.result_medal_nothing
        Medal.Gold -> Res.string.result_medal_gold
        Medal.Silver -> Res.string.result_medal_silver
        Medal.Bronze -> Res.string.result_medal_bronze
        Medal.Star -> Res.string.result_medal_star
    }
