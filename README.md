# Passman
### Simple and Secure!
Passman is an offline console-based password manager for desktop use. It allows you to securely store and manage your passwords and other confidential information. Passman has a simple and easy-to-use command-line interface with various features like add, edit, display, remove, copy, export, scan, clear, help, save, load, exit, and reset.
## Installation

To install Passman, you need to clone this repository to your local machine and then install the required dependencies. You can follow the below steps to get started:

```bash

```

## Usage

To use Passman, you need to download the console table for java (jar file) and add it to the class path of the project [JcTable.jar](https://github.com/Cozmeh/ConsoleTable4java/releases/download/v1.0.0/jcTable.jar)

```bash

```

Once the program starts, you will see a prompt asking you to enter your login password. If it's your first time using Passman, you can create an account by typing '!newAccount'. After entering the password, you will see the Passman console.

### Commands

Passman has the following commands:

- `add`: To add new credentials to the manager.
- `edit`: To edit existing credentials (Note: Service name is not editable).
- `display`: To display existing credentials.
- `remove`: To remove existing credentials.
- `copy`: To copy the password to the clipboard.
- `export`: To export the saved data (Encrypted or Decrypted) into any type of text file (Specify file extension).
- `scan`: To scan the credentials and give a detailed report.
- `clear`: To clear the terminal screen.
- `help`: To display the help message.
- `save`: To save the data.
- `load`: To load the saved data.
- `exit`: To exit the program.
- `reset`: To change the login password of your account (Current).

You can use these commands by typing the command name and following the on-screen instructions.

## Security

Passman uses custom encryption scheme to encrypt and store your data on the local machine. The encryption key is derived from your credentials, which is hashed using a custom encryption algorithm which spits out messed up string, so every credential will follow a different encryption pattern based on its length. Passman does not store your login password or any other sensitive information , everything is stored in your local system!.

## Contributing

If you would like to contribute to Passman, feel free to submit a pull request. Make sure to follow the project's code style and guidelines.

## License

Passman is released under the UnLicense.See [LICENSE](https://unlicense.org/) for more details.
