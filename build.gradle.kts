plugins {
    id("java")
}

group = "gsu.by"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://releases.aspose.com/java/repo/")
    maven (url = "https://jitpack.io" )
}

dependencies {
    compileOnly ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")

    implementation ("com.github.Cloudmersive:Cloudmersive.APIClient.Java:v3.62")

    implementation("com.deepoove:poi-tl:1.12.2")

    implementation("org.yaml:snakeyaml:2.3")

    testCompileOnly ("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.36")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}