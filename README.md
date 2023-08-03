# Dentest Jetbrains plugin

![Build](https://github.com/prollandoc/intellij-plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/19626.svg)](https://plugins.jetbrains.com/plugin/19626)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/19626.svg)](https://plugins.jetbrains.com/plugin/19626)

## Dentest

<!-- Plugin description -->
Dentest is a behavior-driven development platform that makes your Gherkin features live online so that they can be created and read by non-developers.
This plugin allows developers using Jetbrains IDEs to pull them

See the [Dentest documentation](https://docs.dentest.tech) for more details
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Dentest"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/prollandoc/intellij-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


## Usage

This plugin adds an extra "Dentest" menu entry with two items: "Configuration" and "Pull".
The pull action will trigger the configuration one at the first run, or in case of failure, so you can basically only use this one.

### Configuration

Some pieces of configuration are needed for this plugin to work:
- Dentest API: if you are using Dentest website, let the default value. If you have a custom installation, provide the URL of the API
- Dentest Pull Token: you need a pull token to be able to pull. See [https://docs.dentest.tech/pulling-features#retrieving-your-token](https://docs.dentest.tech/pulling-features#retrieving-your-token)
- Features destination: the directory where you want your feature to be downloaded
- Inline param wrapping string: The step sentence params can be wrapped using the string of your choice, even an empty one. By default, they are wrapped using "double quotes"
- Subsequent script: If you want to execute a script after pulling, specify it here

### Pull

You'll be able to select which features you want to pull by using this action

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template

### Contribute

#### Plugin signing:

```
mkdir cert
cd cert

openssl genpkey\
  -aes-256-cbc\
  -algorithm RSA\
  -out private_encrypted.pem\
  -pkeyopt rsa_keygen_bits:4096
  
openssl rsa\
  -in private_encrypted.pem\
  -out private.pem

openssl req\
  -key private.pem\
  -new\
  -x509\
  -days 365\
  -out chain.crt
```

In `Run > Edit Configurations`, configure the following env vars for Gradle tasks:

| PRIVATE_KEY          | cert/private.pem   |
| CERTIFICATE_CHAIN    | cert/chain.crt     |
| PRIVATE_KEY_PASSWORD | your password      |
| PUBLISH_TOKEN        | your publish token |

Then run the task: `[signPlugin]`
