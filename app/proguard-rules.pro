-keep class com.nvidia.** {*;}
-keep class com.wardrumstudios.** {*;}
-keepclassmembers  class com.nvidia.** {*;}
-keepclassmembers  class com.wardrumstudios.** {*;}
-keep class com.brilliant.cr.GTASA { *; }
-keep class * extends com.wardrumstudios.utils.WarMedia
-keepclasseswithmembernames class * {
     native <methods>;
 }

-keepattributes SourceFile
-keepattributes SourceFile
-renamesourcefileattribute SourceFile
