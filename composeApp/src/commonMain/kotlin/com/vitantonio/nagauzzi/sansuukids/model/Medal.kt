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
internal sealed interface Medal {
    val emojiRes: StringResource
    val descriptionRes: StringResource

    @Serializable
    data object Nothing : Medal {
        override val emojiRes: StringResource get() = Res.string.result_medal_emoji_nothing
        override val descriptionRes: StringResource get() = Res.string.result_medal_nothing
    }

    @Serializable
    data object Gold : Medal {
        override val emojiRes: StringResource get() = Res.string.result_medal_emoji_gold
        override val descriptionRes: StringResource get() = Res.string.result_medal_gold
    }

    @Serializable
    data object Silver : Medal {
        override val emojiRes: StringResource get() = Res.string.result_medal_emoji_silver
        override val descriptionRes: StringResource get() = Res.string.result_medal_silver
    }

    @Serializable
    data object Bronze : Medal {
        override val emojiRes: StringResource get() = Res.string.result_medal_emoji_bronze
        override val descriptionRes: StringResource get() = Res.string.result_medal_bronze
    }

    @Serializable
    data object Star : Medal {
        override val emojiRes: StringResource get() = Res.string.result_medal_emoji_star
        override val descriptionRes: StringResource get() = Res.string.result_medal_star
    }
}
