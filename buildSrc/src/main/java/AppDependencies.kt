import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    val annotation = "androidx.annotation:annotation:${Versions.annotation}"
    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    val hiltCompiler = "androidx.hilt:hilt-compiler:1.1.0"
    val daggerAndroid = "com.google.dagger:hilt-android:${Versions.hilt_dagger}"
    val daggerCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt_dagger}"
    val daggerGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_dagger}"
    val retrofitMain = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val okhttpMain = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    val glideRuntime = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    val glideOkhttpIntegration = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    val chuckerDebug = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    val networkResponse = "com.github.haroldadmin:NetworkResponseAdapter:${Versions.network_response}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val ktxActivity = "androidx.activity:activity-ktx:${Versions.ktx_activity}"
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    val splashscreen = "androidx.core:core-splashscreen:${Versions.splashscreen}"

    //android ui
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private val material = "com.google.android.material:material:${Versions.material}"

    //test libs
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private val mockitoCore = "org.mockito:mockito-core:5.15.2"
    private val mockwebserver = "com.squareup.okhttp3:mockwebserver:4.10.0"
    private val archCoreTesting = "androidx.arch.core:core-testing:2.2.0"
    private val turbine = "app.cash.turbine:turbine:1.2.0"
    private val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0"
    private val pagingCommon = "androidx.paging:paging-common-ktx:3.3.5"
    private val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(material)
        add(coroutineCore)
        add(coroutineAndroid)
        add(multidex)
        add(annotation)
        add(lifecycleExtensions)
        add(lifecycleLivedata)
        add(lifecycleViewmodel)
        add(daggerAndroid)
        add(retrofitMain)
        add(retrofitMoshi)
        add(okhttpMain)
        add(okhttpLogging)
        add(glideRuntime)
        add(glideOkhttpIntegration)
        add(moshiKotlin)
        add(networkResponse)
        add(timber)
        add(ktxActivity)
        add(roomKtx)
        add(roomRuntime)
        add(paging)
        add(splashscreen)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(mockwebserver)
        add(espressoContrib)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
        add(mockitoCore)
        add(mockwebserver)
        add(archCoreTesting)
        add(turbine)
        add(kotlinCoroutinesTest)
        add(pagingCommon)
    }

    val compilerLibraries = arrayListOf<String>().apply {
        add(lifecycleCompiler)
        add(hiltCompiler)
        add(daggerCompiler)
        add(glideCompiler)
        add(moshiCodeGen)
        add(hiltCompiler)
        add(roomCompiler)
    }
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}