
# Animated Dialog
![메인 썸네일](https://github.com/uuranus/animated-dialog-compose/assets/72340294/33c0c95b-86b0-400f-849b-20aa8092939a)


# Screenshots

<table>
<tr>
<th> Name </th> <th> Screenshot </th> <th> Usage </th>
</tr>
<tr>
<td> HorizontalExpandDialog </td>
<td><img width="300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/81195d97-6eef-42ec-bd14-70fe22b64f8e"> </td>
<td>
    
```kotlin

HorizontalExpandDialog(
    onDismissRequest = {
        showDialog = false
    }
) {
    // content
}
```

</td>
</tr>
<tr>
<td> VerticalExpandDialog </td>
<td><img width="300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/6fc638b0-3124-4685-b432-c8ae974ffff2"></td>
<td>
    
```kotlin

VerticalExpandDialog(
    onDismissRequest = {
        showDialog = false
    }
) {
    // content
}
```

</td>
</tr>
<tr>
<td> DropDownDialog </td>
<td><img width = "300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/7bdb74de-8667-47a0-b664-7749194491a8"> </td>
<td>
    
```kotlin

DropDownDialog(
    onDismissRequest = {
        showDialog = false
    }
) {
    // content
}
```

</td>
</tr>
<tr>
<td> PopUpDialog </td>
<td><img width="300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/4405f1fa-2527-4f28-8f6a-afa7001604ba"></td>
<td>
    
```kotlin

PopUpDialog(
    onDismissRequest = {
        showDialog = false
    }
) {
    // content
}
```

</td>
</tr>
</table>

# Feature

<table>
<tr>
<th> Feature </th> <th> Screenshot </th> <th> Usage </th>
</tr>
<tr>
<td> Shape </td>
<td><img width="300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/24beeebe-db67-4821-bfbe-1c8caf0b1026"> </td>
<td>
    
```kotlin
HorizontalExpandDialog(
    onDismissRequest = {
        showDialog = false
    },
    containerProperties = ContainerProperties(
        shape = CutCornerShape(12.dp)
    )
) {
    //content
}
```

</td>
</tr>
<tr>
<td> Color </td>
<td><img width="300" src="https://github.com/uuranus/animated-dialog-compose/assets/72340294/b7cdd1f8-ca1a-4506-9225-834bf89b8ba1"></td>
<td>
    
```kotlin

VerticalExpandDialog(
    onDismissRequest = {
        showDialog = false
    },
    containerProperties = ContainerProperties(
        color = MaterialTheme.colorScheme.errorContainer,
        shape = RoundedCornerShape(0.dp)
    )
) {
    //content
}
```

</td>
</tr>

</table>

# Get Started
1. Add the JitPack repository to your build file (in your root build.gradle)
``` kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
```

2. Add the dependency
``` kotlin
dependencies {
    implementation("com.github.uuranus:animated-dialog-compose:latest-version")
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
