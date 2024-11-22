## Провет позволяет генерировать билеты на основе списка вопросов и шаблонного билета.

 __Стек:__ java, lombok, snakeyaml, deepove, cloudmersive api, gradle(kotlin)

__Конфигурация:__
- Файл конфигурации src/main/resources/config.yaml
- Параметры:
  - template_file - имя шабонного файла
  - questions_file - имя файла с вопросами
  - output_file - имя итогового файла
  - number_of_tickets - количество билетов, которы хотите сгенерировать
  - api_key - апи ключ для возможности генерации

 - Получение Api ключа:
   - Заходим на https://portal.cloudmersive.com
   - Регистрируемся
   - Выбираем план
   - Выбираем роль
   - Выбираем api Document and Data Conversion API
   - Выбираем язык java
   - Нажимаем Create Key и подтверждаем почту
   - Вуаля ключ создан.
   - В бесплатной версии api ключ позволяет делать 1 запрос в секунду и 800 запросов в месяц 
