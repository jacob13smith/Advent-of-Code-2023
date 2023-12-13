use std::collections::HashMap;
use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;
use num::integer::lcm;

fn main() {
    if let Ok(lines) = read_lines("./input.txt") {
        let mut line_idx = 0;
        let mut instructions = String::new();
        let mut map:HashMap<String, (String, String)> = HashMap::new();
        let mut curr_elements = vec![];
        for line in lines {
            if let Ok(line_str) = line {
                if line_idx == 0 {
                    instructions = line_str;
                } else if line_idx != 1 {
                    let parts = line_str.split(" = ").collect::<Vec<&str>>();
                    let mut tuple_part = parts[1].chars();
                    tuple_part.next();
                    tuple_part.next_back();
                    let tuples = tuple_part.as_str().split(", ").collect::<Vec<&str>>();
                    map.insert(parts[0].to_string(), (tuples[0].to_string(), tuples[1].to_string()));

                    if parts[0].chars().last().unwrap() == 'A' {
                        curr_elements.push(String::from(parts[0]));
                    }
                }
                line_idx += 1;
            }
        }

        let mut prod = 1;
        for element in curr_elements {
            let mut curr = element;
            let mut i = 0;
            while curr.chars().last().unwrap() != 'Z' {
                if instructions.chars().nth(i % instructions.len()).unwrap() == 'L' {
                    curr = map.get(&curr).unwrap().0.to_string(); 
                } else {
                    curr = map.get(&curr).unwrap().1.to_string();
                }

                i += 1;
            }
            println!("Found at step {}", i);
            prod = lcm(prod, i);
        }

        println!("{}", prod);

    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
