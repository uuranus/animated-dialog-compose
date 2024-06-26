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

# License
```
Copyright 2024 uuranus All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
