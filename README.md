
# Currency Converter App

This is a Currency Converter Android app developed using Kotlin and Retrofit for currency conversion functionality. It allows users to convert between different currencies using live exchange rates. The app includes a custom menu, currency spinners with search functionality, and a clear, user-friendly interface.

## Features

- **Currency Conversion**: Convert an amount from one currency to another using live exchange rates.
- **Custom Menu**: A popup menu for additional functionalities like tracking favorites and cryptocurrencies.
- **Searchable Spinners**: Choose currencies from searchable lists, allowing for efficient and accurate selection.
- **Error Handling**: Alerts and dialogs to handle errors and provide user feedback.

## Technologies Used

- **Kotlin**: Programming language used for Android development.
- **Retrofit**: HTTP client for making network requests.
- **Gson**: JSON converter for handling API responses.
- **PopupMenu**: For displaying a custom menu.
- **Dialogs**: For showing alerts and input dialogs.

## Getting Started

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/your-repository.git
   ```

2. **Setup Dependencies**

   Make sure you have the following dependencies in your `build.gradle` file:

   ```groovy
   implementation 'com.squareup.retrofit2:retrofit:2.9.0'
   implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
   ```

3. **API Key**

   Replace the placeholder API key `"c3852155f648f6ed278ace03"` with your actual API key from the currency conversion service.

4. **Run the App**

   Build and run the app on an Android device or emulator.

## How to Use

1. **Select Currencies**: Tap on the `from` or `to` currency spinner to choose the desired currencies from the searchable list.
2. **Enter Amount**: Enter the amount you want to convert in the input field.
3. **Convert**: Tap the convert button to see the converted amount.
4. **Menu Options**: Use the custom menu for additional features.

## Code Structure

- **ApiService**: Interface defining the Retrofit API endpoints for currency conversion.
- **CustomMenu**: Class handling the custom popup menu functionality.
- **ConversionResponse**: Data class for mapping the API response.
- **MainActivity**: Main activity handling the UI and conversion logic.

## Screenshots

![IMG-20240804-WA0017](https://github.com/user-attachments/assets/1251d625-53ab-48cb-bc6a-dc0bb4fff465)                ![IMG-20240804-WA0019](https://github.com/user-attachments/assets/5c7cef9d-30b0-48e7-b0d8-fcb817255ab5)


![IMG-20240804-WA0021](https://github.com/user-attachments/assets/a84a6713-f481-4cdb-b751-2a26255d9458)                ![IMG-20240804-WA0018](https://github.com/user-attachments/assets/55176b7c-56ad-43ee-8d14-79b3dec3725d)
    
![IMG-20240804-WA0022](https://github.com/user-attachments/assets/d4a6d014-f603-40ad-9a30-8336dd6d76be)                ![IMG-20240804-WA0020](https://github.com/user-attachments/assets/5a3a86f3-e46a-4d54-a0a3-85a6ebf33e19)


## Contributing

Contributions are welcome! Please open an issue or submit a pull request if you have any suggestions or improvements.

