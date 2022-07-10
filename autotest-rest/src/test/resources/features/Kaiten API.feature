#language:ru
@test

Функционал: Trello API_bdd

  Сценарий: Создание и настройка элементов системы Kaiten

    Когда создать контекстные переменные
      | name | gaa93843 |
  #Создание нового Space
    И создать запрос создания нового Space
      | method | url                                         | body             |
      | POST   | https://${name}.kaiten.ru/api/latest/spaces | addNewSpace.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И статус код 200

  #Создание нового Board in Space
    И создать запрос создания новой Space Board
      | method | url                                                           | body             |
      | POST   | https://${name}.kaiten.ru/api/latest/spaces/${spaceID}/boards | addNewBoard.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И Новая Space Board статус код 200

  #Создание нового Card in Board
    И создать запрос создания новой Board Card
      | method | url                                        | body            |
      | POST   | https://${name}.kaiten.ru/api/latest/cards | addNewCard.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И Новая Board Card статус код 200

  #Добавление нового файла в Card
    И создать запрос добавления файла в Card
      | method | url                                                        |
      | PUT    | https://${name}.kaiten.ru/api/latest/cards/${cardID}/files |
    И добавить header
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
      | Accept        | application/json                            |
      | Content-Type  | multipart/form-data                         |
    И добавить файл в Card
      | src/test/resources/files/photo1.jpg |
    Тогда отправить запрос
    И добавление файла в Card статус код 200

  #Назчанить срок выпонения  Card
    И создать запрос назначения срока выполения Card
      | method | url                                                  | body                     |
      | PATCH  | https://${name}.kaiten.ru/api/latest/cards/${cardID} | addDeadlineDataCard.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И новый срок выполнения Card статус код 200

  #Добавить описание Card
    И создать запрос назначения срока выполения Card
      | method | url                                                  | body                    |
      | PATCH  | https://${name}.kaiten.ru/api/latest/cards/${cardID} | addDescriptionCard.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И добавлено описание Card статус код 200

  #Создать чеклист в Card
    И создать запрос создания чеклиста в Card
      | method | url                                                             | body                    |
      | POST   | https://${name}.kaiten.ru/api/latest/cards/${cardID}/checklists | addCheckListToCard.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И создание чеклиста в Card статус код 200

  #Добаваить пункт 1 в чеклист Card
    Пусть в чеклист добавлен пункт1
    И создать запрос добавления пункта в чеклист Card
      | method | url                                                                  | body                      |
      | POST   | https://${name}.kaiten.ru/api/latest/checklists/${checklistID}/items | addItemCheckListCard.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И добавление первого пункта в чеклист Card статус код 200

  #Добаваить пункт 2 в чеклист Card
    Пусть в чеклист добавлен пункт2
    И создать запрос добавления пункта в чеклист Card
      | method | url                                                                  | body                      |
      | POST   | https://${name}.kaiten.ru/api/latest/checklists/${checklistID}/items | addItemCheckListCard.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И добавление второго пункта в чеклист Card статус код 200

  #Отметить пункт 1 в чеклист Card
    И создать запрос отметки пункта чеклиста Card
      | method | url                                                                                         | body                         |
      | PATCH  | https://${name}.kaiten.ru/api/latest/checklists/${checklistID}/items/${newItemCheckListID1} | setItemCheckListChecked.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И отметка  пункта чеклиста Card статус код 200

  #Создать новую колонку Card
    И создать запрос добавления колонки Card
      | method | url                                                                 | body              |
      | POST   | https://${name}.kaiten.ru/api/latest/boards/${spaceBoardID}/columns | addNewColumn.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И создание новой колонки Card статус код 200

  #Переместить карточку в новую колонку Card
    И создать запрос перемещение карточки в новую колонку Board
      | method | url                                                  | body                  |
      | PATCH  | https://${name}.kaiten.ru/api/latest/cards/${cardID} | moveCardToColumn.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И перемещение карточки в новую колонку Board статус код 200

  #Отметить пункт 2 в чеклисте Card
    И создать запрос отметки пункта чеклиста Card
      | method | url                                                                                         | body                         |
      | PATCH  | https://${name}.kaiten.ru/api/latest/checklists/${checklistID}/items/${newItemCheckListID2} | setItemCheckListChecked.json |
    И добавить header
      | Accept        | application/json                            |
      | Content-Type  | application/json                            |
      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
    Тогда отправить запрос
    И отметка  пункта чеклиста Card статус код 200

#  #Переместить карточку в новую колонку Card
#    И создать запрос перемещение карточки в архив Board
#      | method | url                                                  | body                   |
#      | PATCH  | https://${name}.kaiten.ru/api/latest/cards/${cardID} | moveCardToArchive.json |
#    И добавить header
#      | Accept        | application/json                            |
#      | Content-Type  | application/json                            |
#      | Authorization | Bearer ef68b8be-db98-4c99-985d-90584c9487aa |
#    Тогда отправить запрос
#    И перемещение карточки в архив Board статус код 200

