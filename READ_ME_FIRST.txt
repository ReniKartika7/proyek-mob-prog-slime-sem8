IMPORTANT NOTES!!
=================
Sebelum menjalankan aplikasi SLIME perlu membuka file "github.properties" pada text editor.
gpr.usr dan gpr.key perlu diisi dengan ketentuan seperti berikut :

1. Untuk mengisi gpr.usr (dengan user id GitHub) dapat mengakses link berikut :
- https://api.github.com/users/[username github Anda]
- setelah itu lihat pada bagian "id" dan di copy-paste ke gpr.user di "github.properties"

2. Untuk mengisi gpr.key perlu membuat personal access token baru dengan cara :
- Login GitHub -> Settings -> Developer Settings -> Personal Access Tokens -> Generate New Token
- check bagian "read:packages" -> tekan tombol "Generate Token"
- copy personal access token yang baru saja digenerate dan paste ke file "github.properties"

Untuk selengkapnya dapat dilihat pada link GitHub sebagai berikut :
https://github.com/Cuberto/liquid-swipe-android