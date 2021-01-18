# Apache Camel and ActiveMQ

## Описание задания
С помощью Apache Camel написать программу, реализующую передачу сообщения из одной очереди Apache ActiveMQ в другую.  
Формат исходного сообщения base64. Программа должна пересылать сообщение в раскодированном виде.  
Добавить обработку ошибок при получении пустого сообщения в виде вывода произвольного текста ошибки в консоль Java. Пустым сообщением считать сообщение, не содержащее ни одного символа.

## Запуск приложения
    java -jar <file_name> <options>
* <file_name> - имя файла, получившегося при сборке проекта (обычно имеет расширение .jar)
* &lt;options&gt; - дополнительные опции

### Опции
* -u, --url - после неё нужно указать тип подключения, имя хоста и порт ActiveMQ сервера (по умолчанию _tcp://localhost:61616_) __(например: _-u tcp://localhost:61616_)__
* -i, --input - после неё нужно указать имя очереди, из которой будут браться сообщения (по умолчанию _Input_) __(например: _-i Input_)__
* -o, --output - после неё нужно указать имя первой очереди, в которую будут записываться сообщения (по умолчанию _Output_) __(например: _-o Output_)__
* -t, --type - после неё нужно указать тип кодированию методом Base64, варианты: _url_, _mime_ или _default_ (по умолчанию _default_) __(например: _-t default_)__

Все опции являются не обязательными.  
Также если имена очередей будут одинаковыми, то приложение выведет сообщение об ошибке.

## Обработка ошибок
Если из входной очереди считается пустое сообщение с пустым телом, то в консоль выведется ссобщение "The message is empty".