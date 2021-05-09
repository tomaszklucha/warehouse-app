provider "aws" {
  region = "eu-west-1"
  access_key = ""
  secret_key = ""
}

resource "aws_db_instance" "app" {
  allocated_storage = 10
  engine = "postgres"
  engine_version = "13.1"
  instance_class = "db.t3.micro"
  name = "mydb"
  username = "postgres"
  password = "postgres"
  skip_final_snapshot = true
}

resource "aws_lb" "app" {
  name = "warehouse-app-lb"
  internal = false
  load_balancer_type = "application"
  subnets = ["subnet-01dad97b2582c5f56", "subnet-0ac34ad11d3071141", "subnet-06422c25ff6176bbc"]
}

resource "aws_lb_listener" "app" {
  load_balancer_arn = aws_lb.app.arn

  port = "80"
  protocol = "HTTP"

  default_action {
    type = "forward"
    forward {
      target_group {
        arn = aws_lb_target_group.blue.arn
        weight = lookup(local.traffic_dist_map[var.distribution], "blue", 100)
      }

      target_group {
        arn = aws_lb_target_group.green.arn
        weight = lookup(local.traffic_dist_map[var.distribution], "green", 0)

      }

      stickiness {
        enabled = false
        duration = 1
      }
    }
  }
}
