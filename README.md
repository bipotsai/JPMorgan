JP Morgan Interview Test
===================================

Code Structure
-----------------------------------
1.com.jpmorgan.interview.entity
  * TradeEntity : trade record entity

2.com.jpmorgan.interview.enums
  * Currency: developer could make a specified workweek config here, and don't have to modify main business logic
  * Instruction : client's instruction of BUY/SELL

3.com.jpmorgan.interview.exception
  * InstructionNotExistException : throws this when the invalid instruction of trade record
  * InvalidWorkDayException : future expansion, developer could add none valid workweek Currency.

4.com.jpmorgan.interview.utill
  * TradeDateUtil : havs two main methods
  * "isWorkDay" : check work day of specified currency
  * "getNextWorkDay" : get next valid work day of specified currency

5.com.jpmorgan.interview.ReportGenerator
  * generate all required report

6.com.jpmorgan.interview.Main
  * tet program