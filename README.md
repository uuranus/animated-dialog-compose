# Animated Dialog
Animated Custom Dialog with Jetpack Compose

# Screenshots
|         HorizontalExpandDialog          |         VerticalExpandDialog          |
|:--------------------------------------:|:------------------------------------:|
| <img width = "300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/81195d97-6eef-42ec-bd14-70fe22b64f8e"> |<img width = "300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/6fc638b0-3124-4685-b432-c8ae974ffff2"> |

|         DropDownDialog                  |         PopUpDialog                 |
|:--------------------------------------:|:----------------------------------:|
| <img width = "300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/7bdb74de-8667-47a0-b664-7749194491a8"> | <img width = "300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/4405f1fa-2527-4f28-8f6a-afa7001604ba"> |


# Usage

You can use the same code by simply changing the name of the dialog.
``` kotlin
HorizontalExpandDialog(
    onDismissRequest = {
        // do something
    },
    horizontalPadding = 32.dp,
) {
    Text(
        "Hold on a Second!",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        color = Color.Black
    )
}
```
