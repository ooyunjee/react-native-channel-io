
# react-native-channel-io

현재 안드로이드 버전만 지원합니다.
문서 업데이트는 작업 중에 있습니다.

## Getting started

`$ npm install react-native-channel-io --save`

### Mostly automatic installation

`$ react-native link react-native-channel-io`


### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-channel` and add `RNChannel.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNChannel.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.hwangjjung.channel.RNChannelPackage;` to the imports at the top of the file
  - Add `new RNChannelPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-channel-io'
  	project(':react-native-channel-io').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-channel-io/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-channel-io')
  	```

## Usage
```javascript
import RNChannel from 'react-native-channel-io';

// TODO: What to do with the module?
RNChannel;
```
  