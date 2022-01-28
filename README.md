# Samples

### Neumorphism

Researching if it possible to create Neumorphic UI with out-of-the-box functionality of Jetpack Compose

#### Idea

In Neumorphism objects appear to extrude from the background. This effect can be reached by drawing two shadows on the top left and bottom right. The shadows should spread smoothly.

Smooth shadow spreading can be implemented in two ways:

1. Gradient shadow
2. Blurred shadow

#### Research results

I've implemented sample UI via Jetpack Compose. There is a button `NeumorphButton`, which listens users actions with `pointerInteropFilter`. Action events change the button's state `ButtonState`.

Most challenging issue is to implement shadows smooth spreading.

1. I've created button with RadialGradient, like [here](https://proandroiddev.com/compose-snippet-neomorphic-button-7aa7abd43c91). The problem is, that this gradient only for circles, and there is no such out-of-the-box  gradients for rounded rectangles.

2. There is a lib [neumorphic-compose](https://github.com/CuriousNikhil/neumorphic-compose) with blurred shadows. It uses [RenderScript](https://developer.android.com/guide/topics/renderscript/compute) which API will be deprecated starting with Android 12 and lib's author plans a migration. Google recommends migrate to Vulkan.

3. Blur feature was also [requested](https://issuetracker.google.com/issues/166927547?pli=1) in JetpackCompose by developers. You can use it since `1.1.0-alpha03` Compose version, anyway it's only supported for Android 12 and above. Attempts to use this Modifier on older Android versions will be ignored. There is a [doc](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier#(androidx.compose.ui.Modifier).blur(androidx.compose.ui.unit.Dp,androidx.compose.ui.unit.Dp,androidx.compose.ui.draw.BlurredEdgeTreatment)).

#### Useful links

- Explanation what is a neumorphism is: https://fornewid.medium.com/neumorphism-in-android-9cf15e2122dc
- Neumorphic circle button via RadialGradient: https://proandroiddev.com/compose-snippet-neomorphic-button-7aa7abd43c91
- Jetpack Compose Animation docs: https://developer.android.com/jetpack/compose/animation
- Jetpack Compose Gestures docs: https://developer.android.com/jetpack/compose/gestures
- Google recommendations for migrating from RenderScript: https://developer.android.com/guide/topics/renderscript/migrate