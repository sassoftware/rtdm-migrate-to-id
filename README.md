# Java Service Template

This template is used to create a Java service project that uses Gradle to build
an image containing the service. Because this template has multiple value
substitutions needed for generated project content, additional tools are required.

## Substitution Requirements

The [gomplate](https://docs.gomplate.ca/installing/) CLI can be installed and made
available on your `PATH`.

Alternatively, a docker image is available which also provides the gomplate CLI.

### Install locally

Precompiled binaries of gomplate are available from its [GitHub releases page](https://github.com/hairyhenderson/gomplate/releases).

Verify gomplate is successfully installed, executable, and available on your `PATH`:

```bash
$ gomplate --version
gomplate version 0.0.0
```

### Use the docker image

The **Usage** section below indicates how to run using the image instead of a locally installed CLI.
Run the following command to make sure the latest version is pulled and available:

```bash
docker pull pulp.unx.sas.com/cdp-release-x64_oci_linux_2-docker-latest/pipeline-templatization:latest
```

## Usage

1. Create a new repository in GitHub and select this template as the "Repository template".

2. After creating the new repository, clone it locally.

3. From the local repository root folder, create and switch to a new branch, for example `pr-init-template`:

   ```bash
   git switch -c pr-init-template
   ```

4. Modify the included `template-data.yaml` input data file with the appropriate substitution
   values for the provided properties for your new project.

5. Run the provided `template.sh` script which performs the substitution using gomplate,
   git stashes the input data file just in case, and replaces the contents of the static template files
   with the templatized folders and files. After the scripts runs, stage all changes for commit.

   - If gomplate is installed locally:

     ```bash
     ./template.sh
     ```

   - If using the docker image:

     ```bash
     docker run --rm -it -u $(id -u):$(id -g) -v $(pwd):/src \
         pulp.unx.sas.com/cdp-release-x64_oci_linux_2-docker-latest/pipeline-templatization:latest \
         ./template.sh
     ```

   - If you need to start the templatization process over, reset the git index to origin/main.

     **NOTE**: Backup files if necessary first, this will delete **all** local changes.

     ```bash
     git reset --hard origin/main && git clean -dfx
     ```

     The input data file can then be recovered from the stash to start over with.

     ```bash
     git stash apply
     ```

6. When satisified with the results, commit, push, and create the first Pull Request!

   ```bash
   git add -A
   git status
   git commit -m "ci: initial templatization"
   git push -u origin pr-init-template
   ```
