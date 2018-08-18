![alt text](https://github.com/mumayank/Air/blob/master/logo.png "Air")

# Setup
1. In your app-level `gradle.build` file, copy these lines under dependencies section:
```
// for kotlin:
implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

// for kotlin-anko:
implementation "org.jetbrains.anko:anko-coroutines:$kotlin_anko_version"

// for recycler view:
implementation "com.android.support:recyclerview-v7:$support_lib_version"
```
where
`$kotlin_version` is [![Maven Central](https://img.shields.io/maven-central/v/org.jetbrains.kotlin/kotlin-maven-plugin.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.jetbrains.kotlin%22) 
and `$kotlin_anko_version` is [![Download](https://api.bintray.com/packages/jetbrains/anko/anko/images/download.svg) ](https://bintray.com/jetbrains/anko/anko/_latestVersion)

2. Create file `Air.kt` and its copy file contents from [Air.kt file available here](https://github.com/mumayank/Air/blob/master/Air.kt)

# Usage
Coming soon (btw, usage is self-explanatory, if you study Air.kt file)
