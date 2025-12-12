Descripción del Proyecto (README.md Draft)
​Nombre de la Aplicación
​KakoDash 
​Breve Explicación
​KakoDash es una aplicación Android que sirve como proyecto demostrativo, combinando una experiencia de juego simple estilo runner con funcionalidades modernas de gestión de datos. Está construida usando Jetpack Compose para la interfaz de usuario, lo que asegura una arquitectura reactiva y moderna.
​Funcionalidades Clave
​La aplicación se divide en dos módulos principales:
​Módulo de Juego (GameScreen):
​Un juego de carrera infinita donde un personaje (cuadro de color) debe saltar para evitar obstáculos.  
​Permite al usuario personalizar el perfil del jugador (nombre y color) a través de la pantalla EditProfileScreen.  
​Utiliza un bucle de juego implementado en el GameViewModel para simular la física (gravedad, salto) y la colisión.  
​Módulo de Gestión de Datos (ItemsScreen):
​Una sección dedicada a realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre una lista de elementos (Item).  
​La comunicación con el backend (API) se gestiona mediante Retrofit y se abstrae a través del ItemRepository y el ItemViewModel.  
​Sensor Utilizado: Sensor de Proximidad
​El juego utiliza el sensor de proximidad del dispositivo para la interacción con el usuario.  
​Mecanismo de Salto: Cuando la mano del usuario se acerca al sensor (distancia menor a la máxima o cerca de 0), la aplicación detecta este evento y activa la función jump() en el juego.  
​Reinicio del Juego: En la pantalla de Game Over, cubrir el sensor (acercar la mano) reinicia el juego.
![cap1](https://github.com/user-attachments/assets/c4bc7994-dfd4-4b3f-b8cb-a41e12cca0fa)
![cap2](https://github.com/user-attachments/assets/348a479e-3a03-4df9-b403-ff4e8af2724d)
