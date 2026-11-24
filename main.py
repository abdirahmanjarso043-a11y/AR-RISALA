print("BISMILLAH")

while True:
    print("\n========================")
    print("WELCOME TO AR-RISALA")
    print("========================")
    print("1. Learn Quran")
    print("2. Learn Hadith")
    print("3. Learn Islam")
    print("4. About AR-RISALA")
    print("5. Exit")

    choice = input("Choose an option: ")

    if choice == "1":
        print("WELCOME TO THE HOLY QURAN")
        print("Learn the words of Allah.")

    elif choice == "2":
        print("WELCOME TO HADITH")
        print("Learn the teachings of Prophet Muhammad (PBUH).")
        print("HADITH 1: ACTIONS ARE JUDGED BY INTENTIONS")
        print("English: Actions are judged by intentions.")
        print("Kiswahili: Matendo huzingatiwa kwa nia.")
        print("Source: Sahih al-Bukhari 1; Sahih Muslim 1907.")

    elif choice == "3":
        print("WELCOME TO ISLAM")
        print("Learn about your beautiful religion.")

    elif choice == "4":
        print("AR-RISALA")
        print("Knowledge, Faith and Guidance.")

    elif choice == "5":
        print("JAZAKALLAH KHAIR!")
        print("Thank you for using AR-RISALA.")
        break

    else:
        print("Invalid choice. Please try again.")
