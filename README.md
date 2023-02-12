## peliculas-android

Aplicación de visualización de películas y series clasificadas por categorías

La aplicación ofrece una experiencia de usuario intuitiva y atractiva al mostrar listados de películas y series clasificadas por categorías. Además, cuenta con un sistema de ranking para cuantificar y destacar las películas más votadas.

Arquitectura y diseño técnico:

    La aplicación está compuesta por una sola actividad que alberga todos los fragmentos, que a su vez representan las diferentes secciones de la aplicación definidas en el menú lateral.
    La pantalla de inicio muestra varios listados en diferentes vistas de Recicler, cada uno manejado por el mismo adaptador.
    Al hacer clic en una película, se accede a su detalle.
    Al hacer clic en una categoría o en cualquier opción del menú lateral que no sea la pantalla de inicio, se muestra siempre el mismo fragmento con un listado de películas relevantes, siempre manejado por el mismo adaptador.


