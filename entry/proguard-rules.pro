# config module specific ProGuard rules here.
#混淆时开启记录日志
-verbose
#混淆时不使用大小写混合
-dontusemixedcaseclassnames
# 在读取依赖的库文件时，不要略过那些非public类
-dontskipnonpubliclibraryclasses
# 在读取依赖的库文件时，不要略过那些非public类成员
-dontskipnonpubliclibraryclassmembers
# 默认优化次数为5次
-optimizationpasses 5
# 指定要保留的所有可选属性，比如可能会被反射方法调用的属性
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
# 跳过预校验阶段
-dontpreverify
# 保留JNI中调用的类,拥有native方法的类名和方法名，保证可以与native库链接
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留HarmonyOS应用入口类
-keep public class * extends *.aafwk.ability.Ability
-keep public class * extends *.ace.ability.AceAbility
-keep public class * extends *.aafwk.ability.AbilitySlice
-keep public class * extends *.aafwk.ability.AbilityPackage

#没有被使用到的类/方法，可能会在Proguard的压缩/优化阶段被去除。如需关闭压缩/优化功能请在规则文件中配置：
-dontoptimize
-dontshrink
