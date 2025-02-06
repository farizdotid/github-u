# GitHub User Viewer App

A simple Android application to view GitHub users, their details, and repositories using modern Android development practices.

## Features
- Search for GitHub users
- View user profiles and repository details
- Display last viewed users
- Support for infinite scrolling using Paging 3 on user repositories
- Support offline last viewed user

## Tech Stack
- **Kotlin**: Programming language
- **MVVM Architecture**: Structured code organization
- **Retrofit**: HTTP client for API calls
- **Paging 3**: For paginated data handling
- **Hilt**: Dependency injection
- **Coroutines & Flow**: Asynchronous programming
- **Room**: For local database 

## Setup Instructions

1. Clone this repository:
    ```bash
    git clone https://github.com/farizdotid/github-u.git
    cd github-user-viewer
    ```

2. Open the project in Android Studio.

3. Create an `apikey.properties` file in the root of the project and add the following line:
    ```properties
    GITHUB_API_KEY=your_github_personal_access_token
    ```

4. Sync the Gradle project.

5. Run the application on an emulator or physical device.

## API
This application uses the [GitHub REST API](https://docs.github.com/en/rest) to fetch user and repository data.

## Testing
- **Unit Tests**: Written for ViewModels and Repository classes

## Contributions
Contributions are welcome! Feel free to fork the project, create pull requests, or submit issues.

## License
This project is licensed under the [MIT License](LICENSE).

## Contact
For any inquiries, feel free to contact [Fariz Ramadhan](mailto:farizramadhan@example.com).

