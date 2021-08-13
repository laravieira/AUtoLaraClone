# AutoLaraClone
Automatically update minecraft mods, loaders, resources and shaders there are hosted on [Curse Forge](https://www.curseforge.com/minecraft) or GitHub Releases. For loarders it get updated versions for Fabric and Forge on their own websites.
Will also can set up static files as well, you can choose available version of Minecraft and only resources and mods of this version will be downloaded.
## Customization
By default, the app will clone which resources Lara use, for her friends and herself. But to configure it to you, you can open the .jar with Winzip and edit the [config.yml](src/main/resources/config.yml) file.
All configurations can be overwriting by who is installing it on the installation time.

The config.yml file has 7 sections:
1. [General](#general-config)
2. [Profile](#profile-config)
3. [Available versions](#available-versions)
4. [Loaders (fabric, forge)](#loaders-list)
5. [Textures (resourcepacks)](#textures-list)
6. [Shaders (shaderpacks)](shaders-list)
7. [Mods](#mods-list)

### Provides
All resources are downloaded on installation time, this is make using the providers, all available providers are:
* [`corse-forge`](https://www.curseforge.com/minecraft) (requires the project id)
* `github-releases` (requires the project id)
* [`forge-site`](https://fabricmc.net) (only for Forge loader)
* [`fabric-site`](https://files.minecraftforge.net/net/minecraftforge/forge/) (only for Fabric loader)
* `file` (for local, static, not updated, files)

For `file` provider, had to put the file inside a folder called
* "loaders" for loader files
* "resourcepacks" for texture files
* "shaderspacks" for shader files
* "mods" for mods files

And this folder you place on root, next config.yml file.

### General config:
For now, not much, but you can choose if a new folder will be created for the version.
This is the *gamedir*, by default is *.minecraft*, if true, the installation will be on folder *.minecraft/1.17.1* if this version is selected too, that folder will automatically be assigned on the profile.
```yaml
create-versioned-folder: true
```

### Profile config:
Here you can edit some data of the profile that will be created for this installation.
```yaml
profile:
  keep-old: false # This will keep previews installed profiles
  create-new: true # If this is false, all this config will be ignored and no profile will be created
  name: L4R4 # Just the profile name, which will appear on the launcher
  concat-verion: true # If it's true and name is L4R4, the final profile name will be "L4R4 1.17.1"
  icon: # here you but the profile icon, can be the available names on the launcher or base64 png image data
  java-args: # If you want custom args, put here, it will replace the default args for this profile

```

### Available versions:
This will be the list of available minecraft versions on the installation pane.
It is just a string list, don't put custom names here, they are not supported.
```yaml
versions:
  - "1.17.1"
  - "1.17"
  - "1.16.5"
```

### Loaders list:
This is the list of available loaders to install, here you have to put all data about the loader, because they will be downloaded in installation time.
```yaml
loaders:
  # This "loader" key before two dots is the loader identifier, put only a single name to represent the loader.
  loader:
    id: # This is an project number, only necessary for curse or github providers
    name: Loader Name
    # This is the page where who is installing it can see more about
    page: https://loaderpage.com
    tip: load all the mods
    # You probably want that to be true, if false, the loader will not be installed.
    selected: true
    # Where the loader installer is hosted to download
    provider: loader-site
    # The local file name, only necessary for file provider
    file:
```
See all supported providers [here](#provides).


### Textures list:
This is the list of available textures to install, here you have to put all data about the loader, because they will be downloaded in installation time.
```yaml
textures:
  default-dark-mode:
    id: 349116 # the project id
    name: Default Dark Mode # name to display only on installer
    page: # main page link of the texture project
    tip: This is a nice texture
    # You probably want that to be true, if false, the loader will not be installed.
    selected: true
    # Where the texture is hosted to download
    provider: curse-forge
    # The local file name, only necessary for file provider
    file:
```
See all supported providers [here](#provides).

### Shaders list:
This is the list of available shaders to install, here you have to put all data about the loader, because they will be downloaded in installation time.
```yaml
shaders:
  sildurs:
    id: 0 # the project id
    name: Sildurs # name to display only on installer
    page: # main page link of the shader project
    tip: This is a nice shader
    # You probably want that to be true, if false, the loader will not be installed.
    selected: true
    # Where the shader is hosted to download
    provider: file
    # The local file name, only necessary for file provider
    file: SildursShaders-Vibrant-High-1.283.zip
```
See all supported providers [here](#provides).

### Mods list:
This is the list of available mods to install, here you have to put all data about the loader, because they will be downloaded in installation time.
```yaml
mods:
  iris:
    id: 455508 # the project id
    name: Iris # name to display only on installer
    page: # main page link of the shader project
    tip: This is a nice shader loader
    # You probably want that to be true, if false, the loader will not be installed.
    selected: true
    # Where the mod is hosted to download
    provider: curse-forge
    # The local file name, only necessary for file provider
    file: 
    # List of supported loaders by this mod, use the loader identifier key here
    loaders:
      - fabric
      - forge
```
See all supported providers [here](#provides).

See loader identifier [here](#loaders-list).

# helpme
If you wanna change this installer a lot, fork it, clone, do whatever you want.

If you wanna just improve it with more functions, settings, better design, consider open an issue or making a pull request.