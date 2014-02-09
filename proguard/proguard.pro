-libraryjars  <java.home>/lib/rt.jar
-libraryjars  <java.home>/lib/jce.jar
-libraryjars  <java.home>/lib/jsse.jar

-obfuscationdictionary dictionaries/compact.txt
-classobfuscationdictionary dictionaries/shakespeare.txt

# -printmapping proguard.map 
-overloadaggressively 
-defaultpackage '' 
# -flattenpackagehierarchy ''
# -dontusemixedcaseclassnames
# -keeppackagenames
-allowaccessmodification 
-dontoptimize 
# 因为项目中使用到了jpa的Annotation注解，需要保留这个属性
-keepattributes *Annotation*
-keepattributes *Autowired*
-keepattributes *Service*
-keepattributes *Component*

# Keep web filter-class，自己编写的web filter，不能混淆，不然启动服务的时候会报错
-keep public class com.sendtend.sso.filter.* {
	public protected *;
}

# Keep jpa dao, 自身编写的一些dao接口不能混淆，而且如果是有被spring管理的dao, 也应该不进行混淆
-keep public class com.sendtend.sso.dao.* {
	public protected *;
}

# Keep beans managed by spring framework，spring管理的bean不进行混淆
-keep public class com.sendtend.sso.service.* {
	public protected private *;
}

# Keep entity classes extends java.io.Serializable
# 保留jpa中使用到的所有实体类，不进行混淆
-keep public class * implements java.io.Serializable{ 
	public protected private *; 
}

-keep public class com.sendtend.sso.entity.* { 
    public protected private *; 
} 

# Keep - Applications. Keep all application classes, along with their 'main' methods.
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}