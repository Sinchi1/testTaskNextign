# Запуск сервера

  Запускаем файл .jar, который лежит в директории jar, без дополнительный параметров. Должен развернуться на :8080
  
  Далее делаем два post запроса:
  
    1. http://localhost:8080/api/caller/generate?count=30 - создаём 30 записей о абонентах в бд.
    2. http://localhost:8080/api/cdr/generate?count=70 - создаём 70 CDR записей в бд. 

  
	
# Документация

документация JavaDoc по пути javadoc/index.html

#Энд-поинты

http://localhost:8080/api/cdr/generate?count - создание cdr записей.

http://localhost:8080/api/caller/generate?count= - создание абонентов.

http://localhost:8080/api/caller/createCaller?number= - создание абонента с определённым номером.

http://localhost:8080/api/caller/get?phoneNumber= - поиск абонента по телефону.

http://localhost:8080/api/udr/all?year=2025&month=2 - Все UDR записи за ферваль 25 года.

http://localhost:8080/api/udr/subscriber?msisdn=79526239689&entirePeriod=true - Все UDR Записи абонента с номером ... за весь период.

# Задание

Все звонки, совершенные абонентом сотового оператора, собираются на коммутаторах и фиксируются в CDR формате. Когда абонент находится в роуминге за процесс сбора его данных отвечает обслуживающая сеть абонента. Для стандартизации данных между разными операторами, международная ассоциация GSMA ввела стандарт BCE. Согласно ему, данные с CDR должны агрегировать в единый отчет UDR, который впоследствии передается оператору, обслуживающему абонента в домашней сети. На основе этого отчета, домашний оператор выставляет абоненту счет.
Цель задания – смоделировать описанный процесс в упрощенном виде.
Целевой микросервис будет генерировать CDR записи, сохранять их в базу данных и предоставлять Rest-API для получения UDR отчетов и генерации сводного отчета с CDR записями по абоненту.
CDR-запись включает в себя следующие данные:
  тип вызова (01 - исходящие, 02 - входящие);
 номер абонента, инициирующего звонок;
 номер абонента, принимающего звонок;
дата и время начала звонка (ISO 8601);
 дата и время окончания звонка (ISO 8601);
CDR-отчет представляет из себя набор CDR-записей.
разделитель данных – запятая;
разделитель записей – перенос строки;
данные обязательно формируются в хронологическом порядке;
в рамках задания CDR-отчет может быть обычным txt\csv;

Вот пример фрагмента CDR-отчета:

	02,79876543221, 79123456789, 2025-02-10T14:56:12, 2025-02-10T14:58:20
	01,79996667755, 79876543221, 2025-02-10T10:12:25, 2025-02-10T10:12:57
 
UDR представляет из себя объект в формате JSON, который включает в себя номер абонента и сумму длительности его звонков.
Пример UDR объекта

    {
    "msisdn": "79992221122",
    "incomingCall": {
        "totalTime": "02:12:13"
    },
    "outcomingCall": {
        "totalTime": "00:02:50"
    }
    }
 
Задача 1:
Напишите часть, эмулирующую работу коммутатора, т.е. генерирующую CDR записи.
Условия:
1.     Во время генерации создаются CDR записи и сохраняются в локальную БД (h2);
2.     Данные генерируются в хронологическом порядке звонков, т.е. записи по одному абоненту могут прерываться записями по другому абоненту;
3.     Количество и длительность звонков определяется случайным образом;
4.     Установленный список абонентов (не менее 10) хранится в локальной БД (h2);
5.     Один прогон генерации создает записи сразу за 1 год.
Задача 2:
Напишите часть, предоставляющую Rest-API для работы с UDR.
          	Условия:
1.     Требуется REST метод, который возвращает UDR запись (формат предоставлен выше) по одному переданному абоненту. В зависимости от переданных в метод параметров, UDR должен составляться либо за запрошенный месяц, либо за весь тарифицируемый период.
2.     Требуется REST метод, который возвращает UDR записи по всем нашим абонентам за запрошенный месяц.
3.     Данные можно брать только из БД.
