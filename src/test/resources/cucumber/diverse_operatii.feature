#language: ro

Funcţionalitate: Operaţiuni calculator parking

  Structură scenariu: Verific faptul că pot selecta diferite tipuri de parcări
    Dat fiind selectorul de parcări de pe pagină
    Când încerc să accesez elementul
    Şi selectez opţiunea '<opţiune>'
    Atunci opţiunea selectată va avea valoarea '<valoare>'

  Exemple:
    | opţiune                   | valoare |
    | Short-Term Parking        | STP     |
    | Economy Parking           | EP      |
    | Long-Term Surface Parking | LTS     |
    | Long-Term Garage Parking  | LTG     |
    | Valet Parking             | VP      |


  Structură scenariu: Verific preţul pe o zi de parcare pentru diferitele optiuni de tarifare
    Date fiind informaţiile introduse pentru <nrZile> zile de parcare de tip '<tipParcare>'
    Când apăs butonul 'Calculate'
    Atunci durata afişată a parcării este corectă
    Şi costul parcării este <cost>

  Exemple:
    | nrZile |  | tipParcare | cost    |
    | 0      |  | VP         | $ 0.00  |
    | 1      |  | EP         | $ 9.00  |
    | 1      |  | STP        | $ 28.00 |
    | 5      |  | LTG        | $ 60.00 |

