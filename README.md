# androidx-compose-material3-pullrefresh

> [!Important]
> Since Compose Material3 1.2.0-alpha11, the compose team has been officially supported an experimental implementation of this component. Please take a look at `example/migrated-app` or visit the [official documentation](https://developer.android.com/reference/kotlin/androidx/compose/material3/pulltorefresh/package-summary) for more information.
>
> Hope this library can be deprecated soon. :)

This is a simple wrapper for Jetpack Compose Material3's `PullRefreshIndicator`. The official implementation is not compatible with Material 3 theme.

Official documentation for `androidx.compose.material.pullrefresh`
see [here](<https://developer.android.com/reference/kotlin/androidx/compose/material/pullrefresh/package-summary#(androidx.compose.ui.Modifier).pullRefresh(androidx.compose.material.pullrefresh.PullRefreshState,kotlin.Boolean)>).

> [!Note]
> If you already used the pull refresh component from Compose Material.
>
> You only need to modify the package name from `androidx.compose.material.pullrefresh` into `androidx.compose.material3.pullrefresh` to complete the migration.

## Usage

This library only supports using composite build to import to your project because it will be archived when the official library is released.

> [!Note]
> `<androidx-compose-material3-pullrefresh-path>` is the path to this library.
>
> For example, if you put this library in `<your-app>/libraries/androidx-compose-material3-pullrefresh`, then the path is `libraries/androidx-compose-material3-pullrefresh`.
>
> Or you can use absolute path too.

Copy `<androidx-compose-material3-pullrefresh-path>/library/local.properties.template` to `<androidx-compose-material3-pullrefresh-path>/library/local.properties` and follow the instructions in the file to fit your environment.

### For try it out or contribution purpose

Open `<androidx-compose-material3-pullrefresh-path>` in Android Studio.

### Use it in your project

> [!Note]
> If you cannot figure out the instructions below, the easiest way is just to copy the source code from `<androidx-compose-material3-pullrefresh-path>/library/src/main/kotlin` to your project as well.

In your root project's `settings.gradle.kts` or `settings.gradle` file, add the following:

```kotlin
includeBuild("<androidx-compose-material3-pullrefresh-path>/library") {
    dependencySubstitution {
        substitute(module("me.omico.compose:compose-material3-pullrefresh")).using(project(":"))
    }
}
```

In your app module's `build.gradle.kts` or `build.gradle` file, add the following:

```kotlin
dependencies {
    implementation("me.omico.compose:compose-material3-pullrefresh")
}
```

## License

```text
Copyright 2022 The Android Open Source Project
Copyright 2022-2024 Omico

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
