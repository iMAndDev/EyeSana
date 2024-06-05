package ua.nure.maksymburym.eyesana.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import ua.nure.maksymburym.eyesana.R
import ua.nure.maksymburym.eyesana.domain.Languages
import ua.nure.maksymburym.eyesana.domain.Themes
import ua.nure.maksymburym.eyesana.ui.base.kit.SingleButtonContentDialog
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme
import ua.nure.maksymburym.eyesana.utils.clickWithRipple
import ua.nure.maksymburym.eyesana.utils.getStringRes

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = getColorScheme().secondaryContainer)
            .padding(horizontal = 24.dp)
    ) {
        val (title, surface, container, themeButton, langButton) = createRefs()
        Text(
            text = getStringRes(id = R.string.your_profile),
            modifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                },
            color = getColorScheme().primary,
            fontSize = 24.sp,
        )
        Surface(
            modifier = Modifier
                .padding(top = 18.dp)
                .constrainAs(surface) {
                    top.linkTo(title.bottom)
                    width = Dimension.matchParent
                },
            color = getColorScheme().surface,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp,
            onClick = { /*TODO*/ }
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .constrainAs(container) {
                        top.linkTo(container.top)
                        width = Dimension.matchParent
                    }
            ) {
                val (profilePicture, name) = createRefs()
                Image(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .constrainAs(profilePicture) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colorFilter = ColorFilter.tint(getColorScheme().onSurface)
                )
                Text(
                    color = getColorScheme().primary,
                    text = "User",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .constrainAs(name) {
                            top.linkTo(profilePicture.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )

                /* Theme dialog */

                val isThemeDialogVisible = remember { mutableStateOf(false) }
                SingleButtonContentDialog(
                    title = getStringRes(id = R.string.choose_theme),
                    dialogVisibility = isThemeDialogVisible
                ) {
                    ThemeRadioButtons(
                        options = viewModel.getAllThemes(),
                        selectedOption = { viewModel.selectedTheme() },
                        onOptionSelected = {
                            viewModel.setTheme(it)
                        }
                    )
                }
                ActionButton(
                    modifier = Modifier
                        .constrainAs(themeButton) {
                            top.linkTo(name.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(langButton.start)
                        },
                    icon = Icons.Outlined.Palette,
                    name = getStringRes(id = R.string.theme)
                ) {
                    // Open change theme dialog
                    isThemeDialogVisible.value = true
                }

                /* end */

                /* Language dialog */

                val isLangDialogVisible = remember { mutableStateOf(false) }
                SingleButtonContentDialog(
                    title = getStringRes(id = R.string.choose_language),
                    dialogVisibility = isLangDialogVisible
                ) {
                    LangRadioButtons(
                        options = viewModel.getAllLanguages(),
                        selectedOption = { viewModel.selectedLanguage() },
                        onOptionSelected = {
                            viewModel.setLanguage(it)
                        }
                    )
                }
                ActionButton(
                    modifier = Modifier
                        .constrainAs(langButton) {
                            top.linkTo(name.bottom)
                            start.linkTo(themeButton.end)
                            end.linkTo(parent.end)
                        },
                    icon = Icons.Outlined.Translate,
                    name = getStringRes(id = R.string.language)
                ) {
                    // Open change lang dialog
                    isLangDialogVisible.value = true
                }

                /* end */
            }
        }
    }
}

@Composable
fun ThemeRadioButtons(
    options: List<Themes>,
    selectedOption: () -> Themes,
    onOptionSelected: (Themes) -> Unit
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        options.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (item == selectedOption()),
                        onClick = {
                            onOptionSelected(item)
                        }
                    )
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (item == selectedOption()),
                    onClick = { onOptionSelected(item) }
                )
                Text(text = getStringRes(item.nameRes))
            }
        }
    }
}

@Composable
fun LangRadioButtons(
    options: List<Languages>,
    selectedOption: () -> Languages,
    onOptionSelected: (Languages) -> Unit
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        options.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (item == selectedOption()),
                        onClick = {
                            onOptionSelected(item)
                        }
                    )
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (item == selectedOption()),
                    onClick = { onOptionSelected(item) }
                )
                Text(text = getStringRes(item.langNameRes))
            }
        }
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    name: String,
    onClickAction: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .clickWithRipple(cornerRadius = 6.dp) {
                onClickAction()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = getColorScheme().secondary),
            modifier = Modifier
                .size(40.dp)
                .background(color = getColorScheme().secondaryContainer, shape = CircleShape)
                .padding(8.dp)
        )
        Text(
            color = getColorScheme().secondary,
            text = name,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}