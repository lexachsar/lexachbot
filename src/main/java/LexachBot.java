import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Scanner;

public class LexachBot extends TelegramLongPollingBot {

    private String token;

    public void onUpdateReceived(Update update) {

        // Проверяем, есть ли в апдейте сообщение и есть ли в сообщении текст.
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Объявляем переменные.
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage() // Создаем объект класса message
                    .setChatId(chat_id)
                    .setText(message_text);
            try {
                execute(message); // Отправляем наше сообщение пользователю
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return Return bot username
     * If bot username is @LexachBot, it must return 'LexachBot'
     */
    public String getBotUsername() {
        return "lexachbot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return this.token;
    }

    LexachBot(String token) {
        this.token = token;
    }

    LexachBot() {
        System.out.println("Input the token:");
        Scanner sc = new Scanner(System.in);
        token = sc.nextLine();
    }
}
