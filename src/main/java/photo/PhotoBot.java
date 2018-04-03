package photo;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PhotoBot extends TelegramLongPollingBot {

    private String name;
    private String token;

    PhotoBot() {
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

        // Проверяем, есть ли в апдайте сообщение, и есть ли в сообщении текст
        if (update.hasMessage() && update.getMessage().hasText()) {

            // Объявляем переменную с текстом сообщения орт пользователя
            String message_text = update.getMessage().getText();
            // Переменная с айди сообщения пользователя
            long chat_id = update.getMessage().getChatId();

            // Создаем объект класса SendMessage с айди и текстом из двух переменных выше
            SendMessage message = new SendMessage()
                    .setChatId(chat_id)
                    .setText(message_text);

            try {
                // Отправляем наше сообщение пользователю
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        else
            // В противном случае, проверяем, есть ли в апдейте фото
            if (update.hasMessage() && update.getMessage().hasPhoto()) {

            // Объявляем переменные
            long chat_id = update.getMessage().getChatId();

            // Массив с объектами Photo разных размеров
            // Из этого массива можно получать фото большего размера
            List<PhotoSize> photos = update.getMessage().getPhoto();

            // TODO: Разобрать лямбды в коде.
            // Узнаем id фото
            String f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();
            // Узнаем ширину фото
            int f_width = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();
            // Узнаем высоту фото
            int f_height = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();

            // Задаем заголовок фото
            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
            //Создаем новый объект класса SendPhoto из переменных, объявленных выше
            SendPhoto msg = new SendPhoto()
                    .setChatId(chat_id)
                    .setPhoto(f_id)
                    .setCaption(caption);

            try {
                // Вызываем метод чтобы отправить фото с заголовком
                sendPhoto(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
