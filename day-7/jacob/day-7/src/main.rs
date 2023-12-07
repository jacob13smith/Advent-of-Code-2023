use std::cmp::Ordering;
use std::collections::HashMap;
use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {
    if let Ok(lines) = read_lines("./input.txt") {
        let mut hands: Vec<(String, i32)> = Vec::new();

        for line in lines {
            if let Ok(line_str) = line {
                let parts: Vec<&str> = line_str.split_whitespace().collect();
                let new_hand = (String::from(parts[0]), parts[1].parse::<i32>().unwrap());

                let result = hands.binary_search_by(|curr| compare_hands(&new_hand, curr));

                match result {
                    Ok(idx) | Err(idx) => {
                        hands.insert(idx, new_hand);
                    }
                }
            }
        }
        let mut sum = 0;
        for (idx, hand) in hands.iter().enumerate() {
            println!("{}: {}", hand.0, (idx + 1) as i32);
            sum += hand.1 * (idx + 1) as i32;

        }
        println!("{}", sum);

    }
}

fn compare_hands(new_hand: &(String, i32), curr: &(String, i32)) -> Ordering {
    let new_hand_rank = get_pattern_rank(&new_hand.0);
    let curr_rank = get_pattern_rank(&curr.0);
    let char_map: HashMap<char, usize> = HashMap::from([
        ('J', 0),
        ('2', 1),
        ('3', 2),
        ('4', 3),
        ('5', 4),
        ('6', 5),
        ('7', 6),
        ('8', 7),
        ('9', 8),
        ('T', 9),
        ('Q', 10),
        ('K', 11),
        ('A', 12),
    ]);

    if new_hand_rank > curr_rank {
        Ordering::Less
    } else if new_hand_rank < curr_rank {
        Ordering::Greater
    } else {
        for (char1, char2) in curr.0.chars().zip(new_hand.0.chars()) {
            let char1_score = *char_map.get(&char1).unwrap();
            let char2_score = *char_map.get(&char2).unwrap();
            if char1_score > char2_score {
                return Ordering::Greater;
            }
            if char2_score > char1_score {
                return Ordering::Less;
            }
        }
        Ordering::Equal
    }
}

fn get_pattern_rank(hand: &String) -> i32 {
    let mut cards = vec![0; 13];
    let char_map: HashMap<char, usize> = HashMap::from([
        ('J', 0),
        ('2', 1),
        ('3', 2),
        ('4', 3),
        ('5', 4),
        ('6', 5),
        ('7', 6),
        ('8', 7),
        ('9', 8),
        ('T', 9),
        ('Q', 10),
        ('K', 11),
        ('A', 12),
    ]);

    for char in hand.chars(){
        cards[*char_map.get(&char).unwrap()] += 1;
    }
    let j_count = cards[0];
    cards[0] = 0;
    cards.sort();
    cards.reverse();


    if cards[0] > 3 {
        cards[0] + 1 + j_count
    } else if cards[0] == 3 {
        if cards[1] == 2 {
            4
        } else {
            if j_count == 1 {
                5
            } else if j_count == 2 {
                6
            } else {
                3
            }
        }
    } else if cards[0] == 2 {
        if cards[1] == 2 {
            if j_count > 0 {
                4
            } else {
                2
            }
        } else {
            if j_count == 1 {
                return 3;
            } else if j_count == 2 {
                return 5;
            } else if j_count == 3 {
                return 6;
            }
            1
        }
    } else {
        if j_count == 5 || j_count == 4{
            6
        } else if j_count == 3 {
            5
        } else if j_count == 2 {
            3
        } else if j_count == 1 {
            1
        } else {
            0
        }
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
