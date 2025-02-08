const images = Object.values(import.meta.glob('@/assets/Pictures/*.jpg', { eager: true })).map(img => img.default); // hier, lade auto. alle Bilder aus assets/Pictures (sucht alle .jpg Bilder im Ordner)
                                                                                                                    // map(img => img.default) holt die echten Bildpfade.
// Funktion zur zyklischen Bildzuweisung
export function getHouseImageById(id) {
    return images[(id - 1) % images.length]; // Nutze die ID als Index (minus 1, weil IDs bei 1 starten)
  } 
//	•	id - 1 sorgt dafür, dass ID 1 → Bild 0, ID 2 → Bild 1, ID 3 → Bild 2 usw.
//  •	Wenn die ID größer als die Anzahl der Bilder ist, wird die Bildvergabe wieder von vorne begonnen (% images.length).