# Running
## Windows
```.\gradlew.bat clean build copyLwjglNatives run```

## Linux
In `build.gradle` file for the copying task change `windows` to `linux`.

```./gradlew clean build copyLwjglNatives run```

Note: You might encounter lack of permission. In that case simply add ability to execute `gradlew` using `chmod +x`
