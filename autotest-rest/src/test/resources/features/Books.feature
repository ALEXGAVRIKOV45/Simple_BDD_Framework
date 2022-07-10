#language:ru
#@test

Функционал: Trello API_bdd

  Сценарий: Создание и настройка элементов системы Kaiten

    Когда создать контекстные переменные
      | book | Just Spring Integration |

    И создать запрос
      | method | url                                         |
      | GET    | https://api.itbook.store/1.0/search/${book} |
    Тогда отправить запрос
    И статус код 200

    Когда извлечь данные
      | title    | $.books[?(@.title=='${book}')].title    |
      | subtitle | $.books[?(@.title=='${book}')].subtitle |
      | price    | $.books[?(@.title=='${book}')].price    |

    И сравнить значения
      | ${title}    | ==       | Just Spring Integration                          |
      | ${subtitle} | ==       | A Lightweight Introduction to Spring Integration |
      | ${price}    | содержит | 16.99                                            |