# ChatGPT-Android-App

ChatGPT Android app lets you to chat with ChatGPT or generate images by giving a prompt using  
OpenAI's latest models. Just key in your API key and good to go!

* Define a property in your local `gradle.properties` file (sitting in .gradle folder),  
  named `openAIBackendSecretKey` and assign it  
  the [Bearer token from OpenAI API](https://platform.openai.com/account/api-keys)
* This variable should contain the Bearer keyword as follows:    
  `openAIBackendSecretKey="Bearer <YOUR_TOKEN_HERE>"`

* Compile and run the app and chat with the ChatGPT assistant

## Models used:

### Chatting

Model: gpt-3.5-turbo,
API: https://api.openai.com/v1/chat/completions

### Image generation

API: https://api.openai.com/v1/images/generations

## Previews

| <img src="https://raw.github.com/dkexception/ChatGPT-Android-App/main/previews/SelectionScreen.png" style=" width:200px" /> | <img src="https://raw.github.com/dkexception/ChatGPT-Android-App/main/previews/ChatScreen.png" style=" width:200px" /> |
|--|--|
| <img src="https://raw.github.com/dkexception/ChatGPT-Android-App/main/previews/ImageGenerationScreen.png" style=" width:200px" /> | <img src="https://raw.github.com/dkexception/ChatGPT-Android-App/main/previews/MultiScreenLaunch.png" style=" width:200px" /> |
