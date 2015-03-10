#language: ro

Funcţionalitate: Elemente calculator parking

  Structură scenariu: Verific faptul că elementele de pe pagină sunt încărcate cu valori iniţiale standard şi că pot să le editez
    Dat fiind elementul editabil '<element>' de pe pagină
    Când încerc să accesez elementul
    Atunci iniţial are o valoare standard
    Şi pot să-i modific valoarea

  Exemple:
    | element   |
    | EntryTime |
    | ExitTime  |
    | EntryDate |
    | ExitDate  |