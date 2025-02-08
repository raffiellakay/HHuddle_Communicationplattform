const images = Object.values(import.meta.glob('@/assets/Pictures/*.jpg', { eager: true })).map(img => img.default);

// 1️⃣ Initial eine zufällig gemischte Liste der Bilder erstellen
let shuffledImages = [...images].sort(() => Math.random() - 0.5);
let imageIndex = 0; // Index für die aktuelle Vergabe
const houseImageMap = new Map(); // Speichert die Bild-Zuordnung für jede Haus-ID

export function getHouseImageById(id) {
    // 2️⃣ Prüfen, ob das Haus schon ein Bild hat → Falls ja, dasselbe Bild zurückgeben
    if (houseImageMap.has(id)) {
        return houseImageMap.get(id);
    }

    // 3️⃣ Falls alle Bilder einmal vergeben wurden, Liste neu mischen & Index zurücksetzen
    if (imageIndex >= shuffledImages.length) {
        shuffledImages = [...images].sort(() => Math.random() - 0.5);
        imageIndex = 0;
    }

    // 4️⃣ Nächstes zufälliges Bild aus der Liste nehmen
    const assignedImage = shuffledImages[imageIndex];
    houseImageMap.set(id, assignedImage); // Bild-ID-Zuordnung speichern
    imageIndex++; // Nächstes Bild im Array nutzen

    return assignedImage;
}