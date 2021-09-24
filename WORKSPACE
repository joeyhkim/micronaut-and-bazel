# Kotlin rules.
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

rules_kotlin_version = "v21.9.24"

rules_kotlin_sha = "1f81506076c27ad76ddd0d9b6078577cd81ba35110fa653ef9491d02dfa16dda"

http_archive(
    name = "io_bazel_rules_kotlin",
    sha256 = rules_kotlin_sha,
    urls = ["https://github.com/joeyhkim/rules_kotlin/releases/download/%s/rules_kotlin_release.tgz" % rules_kotlin_version],
)

load("@io_bazel_rules_kotlin//kotlin:repositories.bzl", "kotlin_repositories")

kotlin_repositories()

load("@io_bazel_rules_kotlin//kotlin:core.bzl", "kt_register_toolchains")

register_toolchains("//:kotlin_toolchain")

# Docker rules.
http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "59d5b42ac315e7eadffa944e86e90c2990110a1c8075f1cd145f487e999d22b3",
    strip_prefix = "rules_docker-0.17.0",
    urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.17.0/rules_docker-v0.17.0.tar.gz"],
)

load(
    "@io_bazel_rules_docker//repositories:repositories.bzl",
    container_repositories = "repositories",
)

container_repositories()

load("@io_bazel_rules_docker//repositories:deps.bzl", container_deps = "deps")

container_deps()

load(
    "@io_bazel_rules_docker//container:container.bzl",
    "container_pull",
)

container_pull(
    name = "java_base_11",
    registry = "gcr.io",
    repository = "distroless/java",
    # 'tag' is also supported, but digest is encouraged for reproducibility.
    tag = "latest",
)

load(
    "@io_bazel_rules_docker//java:image.bzl",
    _java_image_repos = "repositories",
)

_java_image_repos()

load(
    "@io_bazel_rules_docker//kotlin:image.bzl",
    _kotlin_image_repos = "repositories",
)

_kotlin_image_repos()

# Maven rules.
RULES_JVM_EXTERNAL_TAG = "4.1"

RULES_JVM_EXTERNAL_SHA = "f36441aa876c4f6427bfb2d1f2d723b48e9d930b62662bf723ddfb8fc80f0140"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "ch.qos.logback:logback-classic:1.2.6",
        "com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5",
        "io.micronaut.kotlin:micronaut-kotlin-runtime:3.0.0",
        "io.micronaut:micronaut-aop:3.0.1",
        "io.micronaut:micronaut-context:3.0.1",
        "io.micronaut:micronaut-http-client:3.0.1",
        "io.micronaut:micronaut-http-server-netty:3.0.1",
        "io.micronaut:micronaut-inject-java:3.0.1",
        "io.micronaut:micronaut-inject:3.0.1",
        "io.micronaut:micronaut-runtime:3.0.1",
        "io.micronaut:micronaut-validation:3.0.1",
        "io.micronaut:micronaut-validation:3.0.1",
        "javax.annotation:javax.annotation-api:1.3.2",
        "org.jetbrains.kotlin:kotlin-reflect:1.5.31",
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31",
        "io.micronaut.test:micronaut-test-junit5:3.0.1",
        "io.micronaut.test:micronaut-test-core:3.0.1",
        "org.junit.jupiter:junit-jupiter-api:5.7.2",
        "org.junit.jupiter:junit-jupiter-engine:5.7.2",
        "org.junit.jupiter:junit-jupiter-params:5.7.2",
        "org.junit.platform:junit-platform-console:1.7.2",
        "jakarta.inject:jakarta.inject-api:2.0.0",
    ],
    fetch_sources = True,
    repositories = ["https://repo1.maven.org/maven2"],
)
