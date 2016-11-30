# manifold-api-android-client
Manifold Technology API Client Library for Android

## Usage

### gradle

**Step 1**. Add the JitPack repository to your build file

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

**Step 2**. Add the dependency

    dependencies {
        compile 'com.github.manifoldtechnology:manifold-api-android-client:v1.0.0'
    }
    
### maven

**Step 1**. Add the JitPack repository to your build file

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

**Step 2**. Add the dependency

    <dependency>
        <groupId>com.github.manifoldtechnology</groupId>
        <artifactId>manifold-api-android-client</artifactId>
        <version>v1.0.0</version>
    </dependency>