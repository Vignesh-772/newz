curl --location 'https://fcm.googleapis.com/v1/projects/newz-e1ee3/messages:send' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer ya29.c.c0AY_VpZh2ZUuyKp12IZhIhg2IpBkE-189UfgmVZus8zEWD7p5dwFoESE4eYzsxd7OjeFr2uiYQ1-E5sH9jkL57XLcbVJz6OPX-wAwCjJzm1U254rNKJN82jAIisRNs0NESmnXSo2G2oXvKmpqWCmRERWJjbuDrfTYDKsR87Net5CmYGYlzwWiSbrOHj4JrLvRAZ7LmQZGdtMXFZFo1YtHcmpo6TIuyctf4NwfinAQq4ULCj48VNmo2TgYpVA24fBWWS44gI8o0iPfWkP3yFwMo6N8HdNhkQWgbScEZpSAh25sKqJM5W46j1eMIPOfH56g3vkHsKHG7saQKJ9dkvqt6meGcwglnVu9As5vaaq5hO_Z2e9NbhzLVfNNfAE387P1yOfMguab6xUx0-II7IB9xpM-jzFvIZrpX2kdnFx5coQp93fj3-iJntqj1nqM9I3nx_OjqS8nkOnXsQ0f92nOfyBcgUVvlxV4hSu5u0Isw84Xj37O-W25h-R9FsophckodVibeI0Jfeq5zSJ52XXoniJgrBYrzvSxOQj_QfzraQ7R3qfZ3pJvQmM9WByerf4gVMBb3JJdtJQa3d-jBQ2y12QY9d80shtV_MWjvFZ39mXj8-Y1_qJgOm1WBw4856QmVi8vX4Vs0wYRpYbSqxJyS_kZbB4r48XXSJ93vdhyhU6i8F5VvwWX6sb_rFOZ01UwU4WtorOoaQUQ55e-zap1IFQUzVlwe2lJ_fcnwtFdtiOpnQ5U3IbcbMSQjiebUwJk5xQyc53SVk7_JpYYfgJ3u98zwlV1Md_O-cwl5vd5YRaxXop5pUx_M3WVp1Wy29VXorhse20MpzBF5-Wo5Q2X5ff1XrWY10R2r8QWMWepilY6FYJUm16ouO21425Oxw03Bh2VWYaSahWWzZkIJpkczwIpdigewQ8-aa2Vt4Qrwofbk4myRSiM4lgBgRblFpMszOg3g6X7hu_Ild1WYmf-tsz4jMnk7UsQz_vdgxVXi97Qv8zi264Mrmg' \
--data '{
  "message": {
    "token": "fzqeleR1Qwm8YJHVkjqkO9:APA91bEdyjBnLilvqh5SdjBdlHExsYpa9i9qRRiAA3tQeXBi4Fqq94mvvCi3V37FsCFei8SKfQpuvhnmM3_rUv3aZGnhTtd52tA3-pFFueKTXjBB7fG0OfQyuP3VKdvOfwXkMuyk65ou",
    "android": {
            "data": {
                    "title": "FCM Notification",
                    "body": "Notification from FCM"
                }
        }
  }
}'
