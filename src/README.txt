Dungeons & Dragons Combat Companion:
Save File Structure:

Serialized HashMap<String,HashMap>
"Loot" - Returns HashMap<String,Object> whose data is used to generate the Loot Tab
*"Data":Object[][] containing data that is shown in the Main JTable in the Loot Tab of the Program
*"Notes":String containing all of the notes displayed in the Note JTextArea of the Loot Tab
"Party" - Returns HashMap<String, Object> whose data is used to generate the Party Statistics Tab
*"<Statistic>: <Object that gives value for given Statistic>
"Players" - Returns HashMap<Int,String> whose data correlates between list and array indices and player names used in the Program.
 "<Player Name>" - Returns HashMap<String,Object> that contains data for each player displayed in the Combat Statistics Tab