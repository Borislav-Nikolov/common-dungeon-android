package com.commondnd.ui.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        error = Exception("Preview")
    )
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorSpecProvider: ErrorSpecProvider = ErrorSpecProvider.Default,
    error: Throwable,
    onAction: ((Any) -> Unit)? = null
) {
    ErrorScreen(
        modifier = modifier,
        errorSpec = remember(errorSpecProvider, error) {
            requireNotNull(errorSpecProvider.get(error))
        },
        onAction = onAction
    )
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorSpec: ErrorSpec,
    onAction: ((Any) -> Unit)? = null
) {
    ErrorScreen(
        modifier = modifier,
        image = errorSpec.image,
        title = errorSpec.title,
        subtitle = errorSpec.description,
        primaryAction = errorSpec.primaryAction,
        secondaryAction = errorSpec.secondaryAction,
        alternativeAction = errorSpec.alternativeAction,
        tertiaryAction = errorSpec.tertiaryAction,
        onAction = onAction
    )
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    image: ImageVector = Icons.Rounded.ErrorOutline,
    title: String,
    subtitle: String? = null,
    primaryAction: Pair<Any, String>? = null,
    secondaryAction: Pair<Any, String>? = null,
    alternativeAction: Pair<Any, String>? = null,
    tertiaryAction: Pair<Any, String>? = null,
    onAction: ((Any) -> Unit)? = null
) {
    BasicScreen(
        modifier = modifier,
        image = image,
        title = title,
        subtitle = subtitle,
        primaryAction = primaryAction,
        secondaryAction = secondaryAction,
        alternativeAction = alternativeAction,
        tertiaryAction = tertiaryAction,
        onAction = onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorSpecProvider: ErrorSpecProvider = ErrorSpecProvider.Default,
    error: Throwable,
    onAction: (Any) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_navigate_back)
                        )
                    }
                }
            )
        },
        content = { contentPadding ->
            ErrorScreen(
                modifier = Modifier.padding(contentPadding),
                errorSpecProvider = errorSpecProvider,
                error = error,
                onAction = onAction
            )
        }
    )
}

interface ErrorSpec {

    val image: ImageVector

    val title: String
    val description: String?

    val primaryAction: Pair<Any, String>?
    val secondaryAction: Pair<Any, String>?
    val alternativeAction: Pair<Any, String>?
    val tertiaryAction: Pair<Any, String>?

    companion object {


        operator fun invoke(
            title: String,
            description: String? = null,
            image: ImageVector = Icons.Rounded.ErrorOutline,
            primaryAction: Pair<Any, String>? = null,
            secondaryAction: Pair<Any, String>? = null,
            alternativeAction: Pair<Any, String>? = null,
            tertiaryAction: Pair<Any, String>? = null
        ): ErrorSpec = object : ErrorSpec {
            override val image: ImageVector = image
            override val title: String = title
            override val description: String? = description
            override val primaryAction: Pair<Any, String>? = primaryAction
            override val secondaryAction: Pair<Any, String>? = secondaryAction
            override val alternativeAction: Pair<Any, String>? = alternativeAction
            override val tertiaryAction: Pair<Any, String>? = tertiaryAction
        }
    }
}

interface ErrorSpecProvider {

    fun get(error: Throwable): ErrorSpec?

    companion object {

        val Default: ErrorSpecProvider
            @Composable get() = object : ErrorSpecProvider {
                val title = stringResource(R.string.title_error_generic)
                override fun get(error: Throwable): ErrorSpec? {
                    return ErrorSpec(title = title)
                }
            }

        fun compose(
            vararg errorSpecProvider: ErrorSpecProvider
        ): ErrorSpecProvider = CompositeErrorSpecProvider(
            buildList { errorSpecProvider.forEach { add(it) } }
        )

        fun ErrorSpecProvider.new(
            vararg errorSpecProvider: ErrorSpecProvider
        ): ErrorSpecProvider = CompositeErrorSpecProvider(
            buildList {
                add(this@new)
                errorSpecProvider.forEach { add(it) }
            }
        )
    }
}

class CompositeErrorSpecProvider(
    initialProviders: List<ErrorSpecProvider> = emptyList()
) : ErrorSpecProvider {

    private val providers = mutableListOf<ErrorSpecProvider>().apply {
        initialProviders.forEach { add(it) }
    }

    fun add(errorSpecProvider: ErrorSpecProvider): Boolean = providers.add(errorSpecProvider)

    fun remove(errorSpecProvider: ErrorSpecProvider): Boolean = providers.remove(errorSpecProvider)

    override fun get(error: Throwable): ErrorSpec? {
        for (provider in providers) {
            val spec = provider.get(error)
            if (spec != null) {
                return spec
            }
        }
        return null
    }
}
