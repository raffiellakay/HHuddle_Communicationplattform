<script setup>
import { useRouter } from 'vue-router'


const router = useRouter()


</script>

<template>
    <header>
        <nav>
            <div class="navbar">
                    <a href="#home" class="navbar-element">Placeholder Menü</a>
                    <a href="#home" class="navbar-element">Menü</a>
                    <a href="#home" class="navbar-element">Placeholder Icon</a>
            </div>
        </nav>
    </header>
</template>


<style scoped>

#header {
    width: 100%; 
    max-width: 100%;
    display: flex;
    align-items: center;
    justify-items: space-between;
}

.navbar {
    max-width: 100%;
    display: flex;
    gap: 1rem;


}

.navbar-element {
    text-decoration: none;
    justify-content: center;
    display: flex;
    align-items: center;
    justify-content: space-between;

    
}




</style>