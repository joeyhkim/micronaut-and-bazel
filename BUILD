# Kotlin rules.
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_binary")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("@io_bazel_rules_kotlin//kotlin:core.bzl", "define_kt_toolchain")

# Docker rules.
load("@io_bazel_rules_docker//java:image.bzl", "java_image")
load("@io_bazel_rules_docker//kotlin:image.bzl", "kt_jvm_image")
load("@io_bazel_rules_docker//container:image.bzl", "container_image")
load("@io_bazel_rules_docker//container:container.bzl", "container_push")

define_kt_toolchain(
    name = "kotlin_toolchain",
    api_version = "1.5",  # "1.1", "1.2", "1.3", "1.4", or "1.5"
    jvm_target = "11",  # "1.6", "1.8", "9", "10", "11", "12", or "13",
    language_version = "1.5",  # "1.1", "1.2", "1.3", "1.4", or "1.5"
)

java_plugin(
    name = "service_description_processor_plugin",
    processor_class = "io.micronaut.annotation.processing.ServiceDescriptionProcessor",
    deps = [
        "@maven//:io_micronaut_micronaut_aop",
        "@maven//:io_micronaut_micronaut_inject",
        "@maven//:io_micronaut_micronaut_inject_java",
        "@maven//:io_micronaut_micronaut_validation",
    ],
)

java_plugin(
    name = "type_element_visitor_processor_plugin",
    processor_class = "io.micronaut.annotation.processing.TypeElementVisitorProcessor",
    deps = [
        "@maven//:io_micronaut_micronaut_aop",
        "@maven//:io_micronaut_micronaut_inject",
        "@maven//:io_micronaut_micronaut_inject_java",
        "@maven//:io_micronaut_micronaut_validation",
    ],
)

java_plugin(
    name = "aggregating_type_element_visitor_processor_plugin",
    processor_class = "io.micronaut.annotation.processing.AggregatingTypeElementVisitorProcessor",
    deps = [
        "@maven//:io_micronaut_micronaut_aop",
        "@maven//:io_micronaut_micronaut_inject",
        "@maven//:io_micronaut_micronaut_inject_java",
        "@maven//:io_micronaut_micronaut_validation",
    ],
)

java_plugin(
    name = "bean_definition_inject_processor_plugin",
    processor_class = "io.micronaut.annotation.processing.BeanDefinitionInjectProcessor",
    deps = [
        "@maven//:io_micronaut_micronaut_aop",
        "@maven//:io_micronaut_micronaut_inject",
        "@maven//:io_micronaut_micronaut_inject_java",
        "@maven//:io_micronaut_micronaut_validation",
    ],
)
java_plugin(
    name = "package_configuration_inject_processor_plugin",
    processor_class = "io.micronaut.annotation.processing.PackageConfigurationInjectProcessor",
    deps = [
        "@maven//:io_micronaut_micronaut_aop",
        "@maven//:io_micronaut_micronaut_inject",
        "@maven//:io_micronaut_micronaut_inject_java",
        "@maven//:io_micronaut_micronaut_validation",
    ],
)


java_library(
    name = "micronaut_lib",
    exported_plugins = [
        "service_description_processor_plugin",
        "type_element_visitor_processor_plugin",
        "aggregating_type_element_visitor_processor_plugin",
        "bean_definition_inject_processor_plugin",
        "package_configuration_inject_processor_plugin"
    ],
    exports = [
        "@maven//:io_micronaut_micronaut_aop",
        "@maven//:io_micronaut_micronaut_inject",
        "@maven//:io_micronaut_micronaut_inject_java",
        "@maven//:io_micronaut_micronaut_validation",
    ],
)

java_library(
    name = "micronaut_deps",
    resources = glob([
        "src/main/resources/**/*.xml",
        "src/main/resources/**/*.yml",
    ]),
    exports = [
        "@maven//:ch_qos_logback_logback_classic",
        "@maven//:com_fasterxml_jackson_module_jackson_module_kotlin",
        "@maven//:io_micronaut_kotlin_micronaut_kotlin_runtime",
        "@maven//:io_micronaut_micronaut_context",
        "@maven//:io_micronaut_micronaut_core",
        "@maven//:io_micronaut_micronaut_core_reactive",
        "@maven//:io_micronaut_micronaut_http",
        "@maven//:io_micronaut_micronaut_http_client",
        "@maven//:io_micronaut_micronaut_http_server_netty",
        "@maven//:io_micronaut_micronaut_runtime",
        "@maven//:io_micronaut_micronaut_validation",
        "@maven//:javax_annotation_javax_annotation_api",
        "@maven//:org_jetbrains_kotlin_kotlin_reflect",
        "@maven//:org_jetbrains_kotlin_kotlin_stdlib_jdk8",
    ],
)

kt_jvm_library(
    name = "application",
    srcs = ["src/main/kotlin/ai/zira/Application.kt"],
    deps = [
        ":micronaut_deps",
        ":micronaut_lib",
    ],
)

kt_jvm_binary(
    name = "application_main",
    main_class = "ai.zira.Application",
    runtime_deps = [":application"],
)

kt_jvm_test(
    name = "micronaut_tests",
    srcs = ["src/test/kotlin/ai/zira/DemoTest.kt"],
    args = [
        "--select-package=ai.zira",
    ],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    deps = [
        ":application",
        ":micronaut_deps",
        ":micronaut_lib",
        "@maven//:io_micronaut_test_micronaut_test_core",
        "@maven//:io_micronaut_test_micronaut_test_junit5",
        "@maven//:jakarta_inject_jakarta_inject_api",
        "@maven//:org_junit_jupiter_junit_jupiter_api",
        "@maven//:org_junit_jupiter_junit_jupiter_engine",
        "@maven//:org_junit_jupiter_junit_jupiter_params",
        "@maven//:org_junit_platform_junit_platform_console",
    ],
)

# We provide this base image so that the timestamping of the image works properly.
container_image(
    name = "java_base_image",
    # References container_pull from WORKSPACE.
    base = "@java_base_11//image",
    stamp = True,
)

kt_jvm_image(
    name = "application_image",
    base = ":java_base_image",
    main_class = "ai.zira.Application",
    runtime_deps = [":application"],
)

container_push(
    name = "push_application_image",
    format = "Docker",
    image = ":application_image",
    registry = "gcr.io",
    # Fill in the repository path below.
    repository = "",
    tag = "latest",
)