package echo;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Scanner;

public class EchoBot extends TelegramLongPollingBot {

    private String name;
    private String token;


    EchoBot() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input the name:");
        this.name = sc.nextLine();
        System.out.println("Input the token:");
        this.token = sc.nextLine();
    }


    /**
     * @return bot username from BotFather
     * If bot username is @EchoBot, it must return 'EchoBot'
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
}
