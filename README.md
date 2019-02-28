# ExpanderTextView

Here **ExpanderTextView** come again!. It is just a TextView ,when text line count is more than 3 , it will shrink with ellipsize and read more text.
When select the TextView, it will show whole original text.

## Setup
On project level build.gradle
```
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
	 }
	}
```	
	
On module level build.gradle
```
dependencies {
	        implementation 'com.github.BannerCoe:ExpanderTextView:v1.0.0'
	}
```

Here is the things for further improvements.
- [X] MVP ExpanderTextView
- [X] live on **jitpack**
- [ ] can control line count from xml and kotlin
- [ ] can change CONTINUE_TEXT (currently it's available only **Read more**).
- [ ] can change CONTINUE_TEXT (currently it come with **Blue**).
- [ ] a bit neat animation.


ExpanderTextView is influence from 
* [ReadMoreTextView](https://github.com/bravoborja/ReadMoreTextView)
* [Spantastic text styling with Spans](https://medium.com/androiddevelopers/spantastic-text-styling-with-spans-17b0c16b4568)