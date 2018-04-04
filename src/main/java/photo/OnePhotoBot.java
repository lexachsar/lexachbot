package photo;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OnePhotoBot extends TelegramLongPollingBot {

    private String name;
    private String token;

    OnePhotoBot() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input the name:");
        this.name = sc.nextLine();
        System.out.println("Input the token:");
        this.token = sc.nextLine();
    }


    /**
     * @return bot username from BotFather
     */
    public String getBotUsername() {
        return this.name;
    }

    /**
     * @return bot token from BotFather
     */
    @Override
    public String getBotToken() {
        return this.token;
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            // Объявляем переменные
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            switch (message_text) {
                case "/start": {

                    // Пользователь написал /start
                    SendMessage msg = generateStartMessage(message_text, chat_id);

                    // Отправляем сообщение пользователю
                    sendMyMessage(msg);

                    break;
                }

                case "/pic": {
                    // Пользователь написал /pic
                    SendPhoto msg = generatePicMessage(chat_id);

                    try {
                        sendPhoto(msg); // Вызов метода чтобы отправить фото
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    break;
                }

                case "/markup": {

                    SendMessage msg = generateMarkupMessage(chat_id);

                    sendMyMessage(msg);

                    break;
                }

                case "/hide": {

                    SendMessage msg = generateHideMessage(chat_id);

                    sendMyMessage(msg);

                    break;
                }

                default: {
                    // Неизвестная команда
                    SendMessage msg = generateDefaultMessage(chat_id);

                    sendMyMessage(msg);

                    break;
                }

            }
        }
    }

    /**
     * @param message message, generated and ready to be sent.
     */
    private void sendMyMessage(SendMessage message) {
        try {
            // Отправляем наше сообщение пользователю
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param message_text text of user message
     * @param chat_id chat id of user message
     * @return message, generated for /start command
     */
    private SendMessage generateStartMessage(String message_text, long chat_id) {
        // Пользователь написал /start
        // Создаем объект калссса SendMessage
        return new SendMessage()
                .setChatId(chat_id)
                .setText(message_text);
    }

    /**
     * @param chat_id chat id of user message
     * @return message, generated for /pic command
     */
    private SendPhoto generatePicMessage(long chat_id) {
        // Пользователь написал /pic
        return new SendPhoto()
                .setChatId(chat_id)
                .setPhoto("AgADAgAD2KgxG53wIEptne8K9XdunqHxrA4ABDwdEYJ_h7E5h1MAAgI")
                .setCaption("Photo");
    }

    /**
     * @param chat_id chat id of user message
     * @return message, generated for /markup command (keyboard)
     */
    private SendMessage generateMarkupMessage(long chat_id) {
        // Создаем объект класса SendMessage
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText("Here is your keyboard");
        // Создаем объект класса ReplyKeyboardMarkup
        // Создаем набор клавиш (list из строчек клавиш)
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Создаем строчку клавиш
        KeyboardRow row = new KeyboardRow();
        // Задаем каждую клавишу, можно также добавлять клавиши клавиатуры
        row.add("Row 1 Button 1");
        row.add("Row 1 Button 2");
        row.add("Row 1 Button 3");
        // Добавляем первую строчку к набору клавиш
        keyboard.add(row);

        // Создаем другую строчку
        row = new KeyboardRow();
        // Задаем клавиши для второй строчки
        row.add("Row 2 Button 1");
        row.add("Row 2 Button 2");
        row.add("Row 2 Button 3");
        // Добавляем вторую строчку к набору клавиш
        keyboard.add(row);

        // Присваеваем текущий набор клавиш разметке
        keyboardMarkup.setKeyboard(keyboard);
        // И добавляем его к сообщению
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

    /**
     * @param chat_id chat id of user message
     * @return message, generated for /hide command
     */
    private SendMessage generateHideMessage(long chat_id) {
        SendMessage msg = new SendMessage()
                .setChatId(chat_id)
                .setText("Keyboard hidden");
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        msg.setReplyMarkup(keyboardMarkup);

        return msg;
    }

    /**
     * @param chat_id chat id of user message
     * @return message, generated for unknown command
     */
    private SendMessage generateDefaultMessage(long chat_id) {
        // Неизвестная команда
        // Возвращаем объект класса SendMessage
        return new SendMessage()
                .setChatId(chat_id)
                .setText("Unknown command");
    }

}